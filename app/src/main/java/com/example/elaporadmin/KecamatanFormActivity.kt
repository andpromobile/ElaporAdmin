package com.example.elaporadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.ViewModel.KecamatanViewModel
import com.example.elaporadmin.databinding.ActivityKecamatanFormBinding

class KecamatanFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKecamatanFormBinding
    private lateinit var tvNamaKecamatan: EditText
    private lateinit var btnFormBinding: Button
    private lateinit var toolbarKecamatan:androidx.appcompat.widget.Toolbar
    private val kecamatanViewModel: KecamatanViewModel by viewModels()
    private var id:Int = 0
    private var mode:String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKecamatanFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKomponen()
        cekInput()
    }

    private fun updateData() {
        if (cekInput()){

            kecamatanViewModel.updateKecamatan(
                this.id,
                tvNamaKecamatan.text.toString())

            kecamatanViewModel.observePesanLiveData().observe(
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

    private fun insertData() {
        if (cekInput()){

            kecamatanViewModel.insertKecamatan(
                tvNamaKecamatan.text.toString())

            kecamatanViewModel.observePesanLiveData().observe(this
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

    private fun cekInput():Boolean {
        var cek = false
        if (
            (tvNamaKecamatan.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun setKomponen() {
        toolbarKecamatan = binding.toolbarKecamatan
        setSupportActionBar(toolbarKecamatan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tvNamaKecamatan = binding.frmNamaKecamatan
        tvNamaKecamatan = binding.frmNamaKecamatan
        btnFormBinding = binding.btnFormKecamatan

        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()

            if (mode == "EDIT"){
                this.id = intent.getIntExtra("ID",0)
                tvNamaKecamatan.setText(intent.getStringExtra("NAMAKECAMATAN"))
            }
        }

        btnFormBinding.setOnClickListener {
            if (mode == "INSERT")
                insertData()
            else
                updateData()
        }
    }
}