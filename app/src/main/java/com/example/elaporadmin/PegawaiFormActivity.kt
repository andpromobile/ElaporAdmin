package com.example.elaporadmin

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.ViewModel.BidangViewModel
import com.example.elaporadmin.ViewModel.PegawaiViewModel
import com.example.elaporadmin.databinding.ActivityPegawaiFormBinding

class PegawaiFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPegawaiFormBinding
    private lateinit var frmNIP:EditText
    private lateinit var frmNamaPegawai:EditText
    private lateinit var frmJabatan:EditText
    private lateinit var frmBidangIdPegawai:AutoCompleteTextView
    private lateinit var btnFormPegawai: Button
    private lateinit var toolbarPegawai: androidx.appcompat.widget.Toolbar
    private val pegawaiViewModel:PegawaiViewModel by viewModels()
    private var mode:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPegawaiFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKomponen()
        cekInput()
    }

    private fun cekInput():Boolean {
        var cek = false
        if (
            (frmNIP.text.toString() != "") &&
            (frmNamaPegawai.text.toString() != "") &&
            (frmJabatan.text.toString() != "") &&
            (frmBidangIdPegawai.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun setKomponen() {
        toolbarPegawai = binding.toolbarPegawai
        setSupportActionBar(toolbarPegawai)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        frmNIP = binding.frmNIP
        frmNamaPegawai = binding.frmNamaPegawai
        frmJabatan = binding.frmJabatan
        frmBidangIdPegawai = binding.frmBidangIdPegawai
        btnFormPegawai = binding.btnFormPegawai

        val bidangViewModel = ViewModelProvider(this)[BidangViewModel::class.java]

        bidangViewModel.getBidang()
        bidangViewModel.observeBidangLiveData().observe(
            this@PegawaiFormActivity
        ){ bidangList ->
            val fp:MutableList<String?> = ArrayList()
            for (i in bidangList){
                fp.add(i.namabidang+" - "+i.seksi)
            }

            val arrayAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, R.layout.simple_list_item_1, fp)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            frmBidangIdPegawai.setAdapter(arrayAdapter)

            frmBidangIdPegawai.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    if (parent.getItemAtPosition(position) == "Daftar Bidang") {
                    }
                    else {
//                            val item = parent.getItemAtPosition(position).toString()
//                            Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()

            if (mode == "EDIT"){
                frmNIP.setText(intent.getStringExtra("NIP"))
                frmNamaPegawai.setText(intent.getStringExtra("NAMAPEGAWAI"))
                frmJabatan.setText(intent.getStringExtra("JABATAN"))
                frmBidangIdPegawai.setText(intent.getStringExtra("BIDANG_ID"))
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

            pegawaiViewModel.insertPegawai(
                frmNIP.text.toString(),
                frmNamaPegawai.text.toString(),
                frmJabatan.text.toString(),
                frmBidangIdPegawai.text.toString().toInt()
            )

            pegawaiViewModel.observePesanLiveData().observe(this
            ) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses")
                    .setContentText("DATA BERHASIL DISIMPAN")
                    .setConfirmButton("Iya", {
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

            pegawaiViewModel.updatePegawai(
                frmNIP.text.toString(),
                frmNamaPegawai.text.toString(),
                frmJabatan.text.toString(),
                frmBidangIdPegawai.text.toString().toInt()
            )

            pegawaiViewModel.observePesanLiveData().observe(
                this,
            ) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses")
                    .setContentText("DATA BERHASIL DIUBAH")
                    .setConfirmButton("Iya", {
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
}