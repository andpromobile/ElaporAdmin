package com.example.elaporadmin

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.ViewModel.KelurahanViewModel
import com.example.elaporadmin.ViewModel.PerangkatDesaViewModel
import com.example.elaporadmin.databinding.ActivityPerangkatDesaFormBinding

class PerangkatDesaFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerangkatDesaFormBinding
    private lateinit var tvNamaPd:EditText
    private lateinit var tvKelurahanIdPd:AutoCompleteTextView
    private lateinit var spinner:Spinner
    private lateinit var btnFormBinding:Button
    private lateinit var toolbarPD: androidx.appcompat.widget.Toolbar
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
        toolbarPD = binding.toolbarPD
        setSupportActionBar(toolbarPD)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tvNamaPd = binding.frmNamapd
        tvKelurahanIdPd = binding.frmKelurahanIdPd
//        spinner = binding.spinnerPD
        btnFormBinding = binding.btnFormPd

        val kelurahanViewModel = ViewModelProvider(this)[KelurahanViewModel::class.java]

        kelurahanViewModel.getKelurahan()
        kelurahanViewModel.observeKelurahanLiveData().observe(
        this@PerangkatDesaFormActivity
        ){ kelurahanList ->
                val fp:MutableList<String?> = ArrayList()
                for (i in kelurahanList){
                    fp.add(i.namakelurahan+" - "+i.namakecamatan)
                }

                val arrayAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, android.R.layout.simple_list_item_1, fp)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                tvKelurahanIdPd.setAdapter(arrayAdapter)




                spinner.adapter = arrayAdapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        if (parent.getItemAtPosition(position) == "Daftar Kelurahan") {
                        }
                        else {
                            val item = parent.getItemAtPosition(position).toString()
                            Toast.makeText(parent.context, "Selected: $item with $id", Toast.LENGTH_SHORT).show()

                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }

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

            perangkatDesaViewModel.updatePerangkatDesa(
                this.id,
                tvNamaPd.text.toString(),
                tvKelurahanIdPd.text.toString().toInt())

            perangkatDesaViewModel.observePesanLiveData().observe(
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