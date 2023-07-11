package com.example.elaporadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.example.elaporadmin.ViewModel.PegawaiViewModel
import com.example.elaporadmin.databinding.ActivityPegawaiFormBinding

class PegawaiFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPegawaiFormBinding
    private lateinit var frmNIP:EditText
    private lateinit var frmNamaPegawai:EditText
    private lateinit var frmJabatan:EditText
    private lateinit var frmBidangIdPegawai:EditText
    private lateinit var btnFormPegawai: Button
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
        frmNIP = binding.frmNIP
        frmNamaPegawai = binding.frmNamaPegawai
        frmJabatan = binding.frmJabatan
        frmBidangIdPegawai = binding.frmBidangIdPegawai
        btnFormPegawai = binding.btnFormPegawai

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
                Toast.makeText(
                    applicationContext,
                    it.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

            finish()
        }else{
            Toast.makeText(
                applicationContext,
                "Input Tidak Boleh Kosong!!!",
                Toast.LENGTH_LONG
            ).show()
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
                Toast.makeText(
                    applicationContext,
                    it.toString(),
                    Toast.LENGTH_LONG,
                ).show()
            }

            finish()
        }else{
            Toast.makeText(
                applicationContext,
                "Input Tidak Boleh Kosong!!!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}