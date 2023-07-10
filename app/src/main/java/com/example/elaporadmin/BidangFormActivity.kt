package com.example.elaporadmin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.elaporadmin.ViewModel.BidangViewModel
import com.example.elaporadmin.databinding.ActivityBidangFormBinding

class BidangFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidangFormBinding
    private lateinit var tvNamaBidang:EditText
    private lateinit var tvSeksi:EditText
    private lateinit var btnFormBinding: Button
    private val bidangViewModel:BidangViewModel by viewModels()
    private var id:Int = 0
    private var mode:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidangFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKomponen()
        cekInput()
    }

    private fun updateData() {
        if (cekInput()){

            bidangViewModel.updateBidang(
                this.id,
                tvNamaBidang.text.toString(),
                tvSeksi.text.toString())

            bidangViewModel.observePesanLiveData().observe(
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

    private fun insertData() {
//        bidangViewModel = ViewModelProvider(this)[BidangViewModel::class.java]
        if (cekInput()){

            bidangViewModel.insertBidang(
                tvNamaBidang.text.toString(),
                tvSeksi.text.toString())

            bidangViewModel.observePesanLiveData().observe(this
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

    private fun cekInput():Boolean {
        var cek = false
        if (
            (tvNamaBidang.text.toString() != "") &&
            (tvSeksi.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun setKomponen() {
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
}