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
    private lateinit var nik:EditText
    private lateinit var tvNamaPd:EditText
    private lateinit var tvKelurahanIdPd:AutoCompleteTextView
    private lateinit var emailPd:EditText
    private lateinit var passwordPd:EditText
    private lateinit var btnFormBinding:Button
    private lateinit var toolbarPD: androidx.appcompat.widget.Toolbar
    private val perangkatDesaViewModel:PerangkatDesaViewModel by viewModels()
    private var id:Int = 0
    private var mode:String = ""
    private var kelurahanIdPd:Int = 0

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
            (nik.text.toString() != "") &&
            (tvNamaPd.text.toString() != "") &&
            (tvKelurahanIdPd.text.toString() != "")&&
            (emailPd.text.toString() != "") &&
            (passwordPd.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun setKomponen() {
        toolbarPD = binding.toolbarPD
        setSupportActionBar(toolbarPD)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        nik = binding.nik
        tvNamaPd = binding.frmNamapd
        tvKelurahanIdPd = binding.frmKelurahanIdPd
        emailPd = binding.emailPd
        passwordPd = binding.passwordPd
        btnFormBinding = binding.btnFormPd

        val kelurahanViewModel = ViewModelProvider(this)[KelurahanViewModel::class.java]

        kelurahanViewModel.getKelurahan()
        kelurahanViewModel.observeKelurahanLiveData().observe(
        this@PerangkatDesaFormActivity
        ){ kelurahanList ->
                val fp:MutableList<String?> = ArrayList()
                val listId:MutableList<String?> = ArrayList()
                for (i in kelurahanList){
                    fp.add(i.namakelurahan+" - "+i.namakecamatan)
                    listId.add(i.id.toString())
                }

                val arrayAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, android.R.layout.simple_list_item_1, fp)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                tvKelurahanIdPd.setAdapter(arrayAdapter)

                tvKelurahanIdPd.setOnItemClickListener { _, _, position, _ ->
                    kelurahanIdPd = listId.get(position)!!.toInt()
                }

        }

        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()

            if (mode == "EDIT"){
                nik.setText(intent.getStringExtra("ID"))
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
                nik.text.toInt(),
                tvNamaPd.text.toString(),
                tvKelurahanIdPd.text.toString().toInt(),
                this.kelurahanIdPd,
                emailPd.text.toString(),
                passwordPd.text.toString()
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
                nik.text.toInt(),
                tvNamaPd.text.toString(),
                tvKelurahanIdPd.text.toString().toInt(),
                this.kelurahanIdPd,
                emailPd.text.toString(),
                passwordPd.text.toString()
            )

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