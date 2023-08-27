package com.example.elaporadmin.kelurahan

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.databinding.ActivityKelurahanFormBinding
import com.example.elaporadmin.kecamatan.KecamatanViewModel

class KelurahanFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKelurahanFormBinding
    private lateinit var tvNamaKelurahan:EditText
    private lateinit var btnFormBinding:Button
    private lateinit var kecamatan: AutoCompleteTextView
    private lateinit var toolbarKelurahan:androidx.appcompat.widget.Toolbar
    private val kelurahanViewModel: KelurahanViewModel by viewModels()
    private var id:Int = 0
    private var kecamatanId:Int = 0
    private var mode:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelurahanFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKomponen()
        cekInput()
    }

    private fun updateData() {
        if (cekInput()){

            kelurahanViewModel.updateKelurahan(
                this.id,
                tvNamaKelurahan.text.toString(),
                kecamatanId)

            kelurahanViewModel.observePesanLiveData().observe(
                this,
            ) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses")
                    .setContentText("DATA BERHASIL DIUBAH")
                    .setConfirmButton("Iya") {
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
        if (cekInput()){

            kelurahanViewModel.insertKelurahan(
                tvNamaKelurahan.text.toString(),
                kecamatanId)

            kelurahanViewModel.observePesanLiveData().observe(this
            ) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses")
                    .setContentText("DATA BERHASIL DISIMPAN")
                    .setConfirmButton("Iya") {
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
            (tvNamaKelurahan.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun setKomponen() {
        toolbarKelurahan = binding.toolbarKelurahan
        setSupportActionBar(toolbarKelurahan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tvNamaKelurahan = binding.frmNamaKelurahan
        btnFormBinding = binding.btnFormKelurahan
        kecamatan = binding.frmNamaKecamatan

        val kecamatanViewModel = ViewModelProvider(this)[KecamatanViewModel::class.java]
        kecamatanViewModel.getKecamatan()

        kecamatanViewModel.observeKecamatanLiveData().observe(
            this@KelurahanFormActivity
        ){
            val fp:MutableList<String?> = ArrayList()
            val listId:MutableList<String?> = ArrayList()

            for (i in it){
                fp.add(i.namakecamatan)
                listId.add(i.id.toString())
            }

            val arrayAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, R.layout.simple_list_item_1, fp)
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            kecamatan.setAdapter(arrayAdapter)

            kecamatan.setOnItemClickListener { _, _, position, _ ->
                kecamatanId = listId[position]!!.toInt()

            }
        }

        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()

            if (mode == "EDIT"){
                this.id = intent.getIntExtra("ID",0)
                tvNamaKelurahan.setText(intent.getStringExtra("NAMAKELURAHAN"))
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