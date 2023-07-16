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
import com.example.elaporadmin.ViewModel.KelurahanViewModel
import com.example.elaporadmin.ViewModel.PerangkatDesaViewModel
import com.example.elaporadmin.databinding.ActivityPerangkatDesaFormBinding

class PerangkatDesaFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerangkatDesaFormBinding
    private lateinit var tvNamaPd:EditText
    private lateinit var tvKelurahanIdPd:AutoCompleteTextView
    private lateinit var spinner:Spinner
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
        spinner = binding.spinnerPD
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