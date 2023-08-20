package com.example.elaporadmin.pengaduan

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.DatePicker
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.elaporadmin.MapsActivity
import com.example.elaporadmin.databinding.ActivityPengaduanFormBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class PengaduanFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengaduanFormBinding
    private lateinit var btn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengaduanFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPermissions()
        inisialisasi()
    }

    private fun setPermissions(){
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

    private fun inisialisasi() {
        // Menambahkan aksi untuk tombol Upload/Ambil Gambar
        binding.btnImg.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle("Pilih Aksi")

            val pictureDialogItem:Array<String> = arrayOf("Pilih dari Galeri", "Ambil Gambar")
            pictureDialog.setItems(pictureDialogItem){
                    dialog, which ->
                when(which){
                    0 -> galeri()
                    1 -> kamera()
                }
            }
            pictureDialog.show()
        }

        // Menambahkan aksi untuk tombol peta
        binding.btnPeta.setOnClickListener {
            startActivityForResult(Intent(this, MapsActivity::class.java), 999)
        }

        // Menambahkan aksi untuk input tanggal
        binding.inputTanggal.setOnClickListener {
            val tanggalLapor: Calendar = Calendar.getInstance()
            val date =
                DatePickerDialog.OnDateSetListener {
                        view1: DatePicker?,
                        year: Int, monthOfYear: Int,
                        dayOfMonth: Int ->
                    tanggalLapor.set(Calendar.YEAR, year)
                    tanggalLapor.set(Calendar.MONTH, monthOfYear)
                    tanggalLapor.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val strFormatDefault = "d MMMM yyyy"
                    val simpleDateFormat =
                        SimpleDateFormat(strFormatDefault, Locale.getDefault())
                    binding.inputTanggal.setText(simpleDateFormat.format(tanggalLapor.time))
                }
            DatePickerDialog(
                this@PengaduanFormActivity, date,
                tanggalLapor.get(Calendar.YEAR),
                tanggalLapor.get(Calendar.MONTH),
                tanggalLapor.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
//                binding.inputLokasi.setText(data?.getStringExtra("lokasi"))
            }
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


}