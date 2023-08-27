package com.example.elaporadmin.bidang

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.databinding.ActivityBidangFormBinding

class BidangFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidangFormBinding
    private lateinit var tvNamaBidang:EditText
    private lateinit var tvSeksi:EditText
    private lateinit var btnFormBinding: Button
    private lateinit var toolbarBidang: androidx.appcompat.widget.Toolbar
    private lateinit var progressBar:ProgressBar
    private val bidangViewModel: BidangViewModel by viewModels()
    private var id:Int = 0
    private var mode:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidangFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBidang)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setKomponen()
        cekInput()
    }

    private fun updateData() {
        if (cekInput()){

            showLoading(true)
            bidangViewModel.updateBidang(
                this.id,
                tvNamaBidang.text.toString(),
                tvSeksi.text.toString())

            bidangViewModel.observePesanLiveData().observe(
                this,
            ) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses")
                    .setContentText("DATA BERHASIL DIUBAH")
                    .setConfirmButton("Iya") {
                        showLoading(false)
                        it.dismissWithAnimation()

                        finish()
                    }
                    .show()
            }
        }else{

            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText("Input Tidak Boleh Kosong!!!")
                .show()
        }
    }

    private fun insertData() {
//        bidangViewModel = ViewModelProvider(this)[BidangViewModel::class.java]
        if (cekInput()){
            showLoading(true)
            bidangViewModel.insertBidang(
                tvNamaBidang.text.toString(),
                tvSeksi.text.toString())

            bidangViewModel.observePesanLiveData().observe(this
            ) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses")
                    .setContentText("DATA BERHASIL DISIMPAN")
                    .setConfirmButton("Iya") {
                        showLoading(false)
                        it.dismissWithAnimation()
                        finish()
                    }
                    .show()
            }
        }else{
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText("Input Tidak Boleh Kosong!!!")
                .show()
        }
    }

    private fun cekInput():Boolean {
        var cek = false
        if (
            (tvNamaBidang.text.toString() != "") &&
            (tvSeksi.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun setKomponen() {
        progressBar = binding.progressBar
        showLoading(false)
        toolbarBidang = binding.toolbarBidang
        setSupportActionBar(toolbarBidang)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tvNamaBidang = binding.frmNamaBidang
        tvSeksi = binding.frmSeksi
        btnFormBinding = binding.btnFormBidang

        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()

            if (mode == "EDIT"){
                this.id = intent.getIntExtra("ID",0)
                tvNamaBidang.setText(intent.getStringExtra("NAMABIDANG"))
                tvSeksi.setText(intent.getStringExtra("SEKSI"))
            }
        }

        btnFormBinding.setOnClickListener {
            if (mode == "INSERT")
                insertData()
            else
                updateData()
        }
    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }
}