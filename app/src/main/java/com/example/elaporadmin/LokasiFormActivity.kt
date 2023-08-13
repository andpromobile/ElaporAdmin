package com.example.elaporadmin

import android.Manifest
import android.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.ViewModel.BidangViewModel
import com.example.elaporadmin.ViewModel.LokasiViewModel
import com.example.elaporadmin.databinding.ActivityLokasiFormBinding

class LokasiFormActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLokasiFormBinding
    private lateinit var frmLokasi:EditText
    private lateinit var frmLatitude:EditText
    private lateinit var frmLongitude:EditText
    private lateinit var frmBidangIdLokasi:AutoCompleteTextView
    private lateinit var btnFormLokasi: Button
    private lateinit var toolbarLokasi:androidx.appcompat.widget.Toolbar
    private val lokasiViewModel:LokasiViewModel by viewModels()
    private var id:Int = 0
    private var mode:String = ""
    private var bidangId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokasiFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPermissions()
        setAutoComplete()
        inisialisasi()
    }

    private fun setAutoComplete() {
        val bidangViewModel = ViewModelProvider(this)[BidangViewModel::class.java]

        bidangViewModel.getBidang()
        bidangViewModel.observeBidangLiveData().observe(
            this@LokasiFormActivity
        ){ bidangList ->
            val fp:MutableList<String?> = ArrayList()
            val listId:MutableList<String?> = ArrayList()

            for (i in bidangList){
                fp.add(i.namabidang+" - "+i.seksi)
                listId.add(i.id.toString())
            }

            val arrayAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, R.layout.simple_list_item_1, fp)
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            frmBidangIdLokasi.setAdapter(arrayAdapter)

            frmBidangIdLokasi.setOnItemClickListener { _, _, position, _ ->
                bidangId = listId[position]!!.toInt()

            }


        }
    }

    private fun cekInput():Boolean {
        var cek = false
        if(
            (frmLokasi.text.toString() != "") &&
            (frmLatitude.text.toString() != "") &&
            (frmLongitude.text.toString() != "")
        ) cek = true

        return cek
    }

    private fun inisialisasi() {
        toolbarLokasi = binding.toolbarLokasi
        setSupportActionBar(toolbarLokasi)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        frmLokasi = binding.frmLokasi
        frmLatitude = binding.frmLatitude
        frmLongitude = binding.frmLongitude
        frmBidangIdLokasi = binding.frmBidangIdLokasi
        btnFormLokasi = binding.btnFormLokasi


        binding.btnImg.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle("Pilih Aksi")

            val pictureDialogItem:Array<String> = arrayOf("Pilih dari Galeri",
            "Ambil Gambar")

            pictureDialog.setItems(pictureDialogItem){
                dialog, which ->
                when(which){
                    0 -> galeri()
                    1 -> kamera()
                }
            }

            pictureDialog.show()
        }

        binding.btnPeta.setOnClickListener {
            startActivityForResult(
                Intent(this, MapsActivity::class.java),
                999
            )
        }

        if (!intent.extras?.isEmpty!!){

            mode = intent.getStringExtra("MODE").toString()

            if (mode == "EDIT"){
                this.id = intent.getIntExtra("ID",0)
                frmLokasi.setText(intent.getStringExtra("DATALOKASI"))
                frmLatitude.setText(intent.getStringExtra("LATITUDE"))
                frmLongitude.setText(intent.getStringExtra("LONGITUDE"))
            }
        }

        btnFormLokasi.setOnClickListener {
            if (mode == "INSERT")
                insertData()
            else
                updateData()
        }
    }

    private fun insertData() {
        if (cekInput()){

            lokasiViewModel.insertLokasi(
                frmLokasi.text.toString(),
                frmLatitude.text.toString().toInt(),
                frmLongitude.text.toString().toInt(),
                "rose.png"
            )

            lokasiViewModel.observePesanLiveData().observe(this
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

    private fun updateData() {
        if (cekInput()){

            lokasiViewModel.updateLokasi(
                this.id,
                frmLokasi.text.toString(),
                frmLatitude.text.toString().toInt(),
                frmLongitude.text.toString().toInt(),
                "rose.png"
            )

            lokasiViewModel.observePesanLiveData().observe(
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

    private fun setPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
    }

    private fun kamera(){
        // Menampilkan layar atau Activity untuk mengambil gambar menggunakan kamera HP
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 123)
    }

    private fun galeri(){
        // Menampilkan layar atau Activity untuk mengambil gambar dari galeri foto di HP
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 456)

    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        // Menampung hasil ambil gambar
        // kode = 123 untuk gambar dari penyimpanan lokal
        // kode = 456 untuk gambar dari kamera
        if(requestCode == 123){
            val bmp: Bitmap = data?.extras?.get("data") as Bitmap
            binding.img.setImageBitmap(bmp)
        }else if(requestCode== 456){
            binding.img.setImageURI(data?.data)
        }

        // Menampung hasil pemilihan lokasi
        // dari nilai Latitude dan Longitude
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 999) {
                frmLatitude.setText(data?.getStringExtra("latitude"))
                frmLongitude.setText(data?.getStringExtra("longitude"))
            }
        }

    }





}