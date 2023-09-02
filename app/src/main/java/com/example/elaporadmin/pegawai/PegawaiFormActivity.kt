package com.example.elaporadmin.pegawai

import android.R
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.bidang.BidangViewModel
import com.example.elaporadmin.databinding.ActivityPegawaiFormBinding

class PegawaiFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPegawaiFormBinding
    private lateinit var frmNIP:EditText
    private lateinit var frmNamaPegawai:EditText
    private lateinit var frmJabatan:EditText
    private lateinit var frmBidangIdPegawai:AutoCompleteTextView
    private lateinit var frmEmailPegawai:EditText
    private lateinit var frmPasswordPegawai:EditText
    private lateinit var btnFormPegawai: Button
    private lateinit var toolbarPegawai: androidx.appcompat.widget.Toolbar
    private lateinit var progressBar:ProgressBar
    private val pegawaiViewModel: PegawaiViewModel by viewModels()
    private var mode:String = ""
    private var bidangId:Int = 0
    private var flag:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPegawaiFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKomponen()
        cekInput()
        showLoading(false)
    }

    private fun cekInput():Boolean {
        var cek = false
        if (
            (frmNIP.text.toString() != "") &&
            (frmNamaPegawai.text.toString() != "") &&
            (frmJabatan.text.toString() != "") &&
            (frmBidangIdPegawai.text.toString() != "") &&
            (frmEmailPegawai.text.toString() != "") &&
            (frmPasswordPegawai.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun setKomponen() {
        toolbarPegawai = binding.toolbarPegawai
        setSupportActionBar(toolbarPegawai)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        progressBar = binding.progressBar
        frmNIP = binding.frmNIP
        frmNamaPegawai = binding.frmNamaPegawai
        frmJabatan = binding.frmJabatan
        frmBidangIdPegawai = binding.frmBidangIdPegawai
        frmEmailPegawai = binding.frmEmailPegawai
        frmPasswordPegawai = binding.frmPasswordPegawai
        btnFormPegawai = binding.btnFormPegawai

        val bidangViewModel = ViewModelProvider(this)[BidangViewModel::class.java]

        bidangViewModel.getBidang()
        bidangViewModel.observeBidangLiveData().observe(
            this@PegawaiFormActivity
        ){ bidangList ->
            val fp:MutableList<String?> = ArrayList()
            val listId:MutableList<String?> = ArrayList()

            for (i in bidangList){
                fp.add(i.namabidang)
                listId.add(i.id.toString())
            }

            val arrayAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, R.layout.simple_list_item_1, fp)
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            frmBidangIdPegawai.setAdapter(arrayAdapter)

            frmBidangIdPegawai.setOnItemClickListener { _, _, position, _ ->
                bidangId = listId.get(position)!!.toInt()

            }


        }

        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()

            if (mode == "EDIT"){
                frmNIP.setText(intent.getStringExtra("NIP"))
                frmNamaPegawai.setText(intent.getStringExtra("NAMAPEGAWAI"))
                frmJabatan.setText(intent.getStringExtra("JABATAN"))
//                frmBidangIdPegawai.setText(intent.getStringExtra("BIDANG_ID"))
                frmEmailPegawai.setText(intent.getStringExtra("EMAIL"))
            }
        }

        btnFormPegawai.setOnClickListener {
            if (mode == "INSERT")
                insertData()
            else
                updateData()
        }
    }

    private fun insertData() {
       if (cekInput()){
           showLoading(true)
            pegawaiViewModel.insertPegawai(
                frmNIP.text.toString(),
                frmNamaPegawai.text.toString(),
                frmJabatan.text.toString(),
                this.bidangId,
                frmEmailPegawai.text.toString(),
                frmPasswordPegawai.text.toString(),
            )

            pegawaiViewModel.observePesanLiveData().observe(this
            ) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses")
                    .setContentText("DATA BERHASIL DISIMPAN")
                    .setConfirmButton("Iya", {
                        showLoading(false)
                        it.dismissWithAnimation()

                        finish()
                    })
                    .show()
            }
        }else{
           SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
               .setContentText("Input Tidak Boleh Kosong!!!")
               .show()
        }
    }

    private fun updateData() {
        if (cekInput()){
            showLoading(true)
            pegawaiViewModel.updatePegawai(
                frmNIP.text.toString(),
                frmNamaPegawai.text.toString(),
                frmJabatan.text.toString(),
                this.bidangId,
                frmEmailPegawai.text.toString(),
                frmPasswordPegawai.text.toString(),
            )

            pegawaiViewModel.observePesanLiveData().observe(
                this,
            ) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses")
                    .setContentText("DATA BERHASIL DIUBAH")
                    .setConfirmButton("Iya", {
                        showLoading(false)

                        it.dismissWithAnimation()

                        finish()
                    })
                    .show()
            }
        }else{
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText("Input Tidak Boleh Kosong!!!")
                .show()
        }
    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }
}