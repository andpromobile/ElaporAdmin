package com.example.elaporadmin.seksi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.R
import com.example.elaporadmin.bidang.BidangViewModel
import com.example.elaporadmin.databinding.ActivitySeksiFormBinding
import com.example.elaporadmin.pegawai.PegawaiViewModel

class SeksiFormActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySeksiFormBinding
    private var bidangId:Int = 0
    private var seksiId:Int = 0
    private var mode:String = ""
    private val seksiVM: SeksiViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeksiFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        cekIntent()
        inisialisasi()
        setListener()
    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    private fun setListener() {
        binding.btnFormBidang.setOnClickListener {
            if (mode == "INSERT")
                insertData()
            else
                updateData()
        }
    }

    private fun cekIntent() {
        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()
            seksiId = intent.getIntExtra("SEKSIID",0)

            if (mode == "EDIT"){
                binding.frmSeksi.setText(intent.getStringExtra("SEKSI"))
            }
        }
    }

    private fun cekInput():Boolean {
        var cek = false
        if (binding.frmSeksi.text.toString() != "") cek = true

        return cek
    }

    private fun inisialisasi() {
        val bidangViewModel = ViewModelProvider(this)[BidangViewModel::class.java]
        val frmNamaBidang:AutoCompleteTextView = binding.frmNamaBidang
        bidangViewModel.getBidang()
        bidangViewModel.observeBidangLiveData().observe(
            this@SeksiFormActivity
        ) { bidangList ->
            val fp: MutableList<String?> = ArrayList()
            val listId: MutableList<String?> = ArrayList()

            for (i in bidangList) {
                fp.add(i.namabidang)
                listId.add(i.id.toString())
            }

            val arrayAdapter: ArrayAdapter<String?> =
                ArrayAdapter<String?>(this, android.R.layout.simple_list_item_1, fp)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            frmNamaBidang.setAdapter(arrayAdapter)

            frmNamaBidang.setOnItemClickListener { _, _, position, _ ->
                bidangId = listId.get(position)!!.toInt()

            }
        }
    }

    private fun insertData() {
        if (cekInput()){
            showLoading(true)
            seksiVM.insertSeksi(
                binding.frmSeksi.text.toString(),
                this.bidangId,
            )

            seksiVM.observePesanLiveData().observe(this
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
            seksiVM.updateSeksi(
                seksiId,
                binding.frmSeksi.text.toString(),
                bidangId,
            )

            seksiVM.observePesanLiveData().observe(
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
}