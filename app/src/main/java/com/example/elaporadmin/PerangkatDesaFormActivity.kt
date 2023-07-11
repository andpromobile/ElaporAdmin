package com.example.elaporadmin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.elaporadmin.ViewModel.PerangkatDesaViewModel
import com.example.elaporadmin.databinding.ActivityPerangkatDesaFormBinding

class PerangkatDesaFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerangkatDesaFormBinding
    private lateinit var tvNamaPd:EditText
    private lateinit var tvKelurahanIdPd:EditText
    private lateinit var btnFormBinding:Button
    private val perangkatDesaViewModel:PerangkatDesaViewModel by viewModels()
    private var id:Int = 0
    private var mode:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerangkatDesaFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKomponen()
        cekInput()
    }

    private fun cekInput():Boolean {
        var cek = false
        if (
            (tvNamaPd.text.toString() != "") &&
            (tvKelurahanIdPd.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun setKomponen() {
        tvNamaPd = binding.frmNamapd
        tvKelurahanIdPd = binding.frmKelurahanIdPd
        btnFormBinding = binding.btnFormPd

        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()

            if (mode == "EDIT"){
                this.id = intent.getIntExtra("ID",0)
                tvNamaPd.setText(intent.getStringExtra("NAMAPD"))
                tvKelurahanIdPd.setText(intent.getStringExtra("KELURAHAN_ID"))
            }
        }

        btnFormBinding.setOnClickListener {
            if (mode == "INSERT")
                insertData()
            else
                updateData()
        }
    }

    private fun insertData() {
        if (cekInput()){

            perangkatDesaViewModel.insertPerangkatDesa(
                tvNamaPd.text.toString(),
                tvKelurahanIdPd.text.toString().toInt(),
            )

            perangkatDesaViewModel.observePesanLiveData().observe(this
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

            perangkatDesaViewModel.updatePerangkatDesa(
                this.id,
                tvNamaPd.text.toString(),
                tvKelurahanIdPd.text.toString().toInt())

            perangkatDesaViewModel.observePesanLiveData().observe(
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