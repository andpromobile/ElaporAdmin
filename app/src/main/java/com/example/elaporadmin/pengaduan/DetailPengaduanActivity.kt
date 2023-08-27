package com.example.elaporadmin.pengaduan

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.elaporadmin.databinding.ActivityDetailPengaduanBinding

class DetailPengaduanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPengaduanBinding
    private lateinit var dataPengaduan:Pengaduan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPengaduanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetailPengaduan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        dataPengaduan = Pengaduan()

        if (!intent.extras?.isEmpty!!){

            with(dataPengaduan){
                id = intent.getIntExtra("ID",0)
                nama = intent.getStringExtra("NAMA").toString()
                telp = intent.getStringExtra("TELP").toString()
                judulpengaduan= intent.getStringExtra("JUDUL").toString()
                isipengaduan= intent.getStringExtra("ISI").toString()
                datalokasi= intent.getStringExtra("LOKASI").toString()
                namakecamatan= intent.getStringExtra("KECAMATAN").toString()
                namakelurahan = intent.getStringExtra("KELURAHAN").toString()
                namabidang = intent.getStringExtra("BIDANG").toString()
                tanggalpengaduan = intent.getStringExtra("TANGGAL").toString()
                status = intent.getStringExtra("STATUS").toString()
                foto = intent.getStringExtra("FOTO")
            }
        }

        when(dataPengaduan.status){
            "0" -> {
                binding.status.text = "PENDING"
                binding.status.setBackgroundColor(Color.parseColor("#900C3F"))
            }
            "1"-> {
                binding.status.text = "Diteruskan Ke Kepala Bidang"
                binding.status.setBackgroundColor(Color.parseColor("#F94C10"))
            }
            "2"-> {
                binding.status.text = "Telah Diverifikasi Kepala Bidang"
                binding.status.setBackgroundColor(Color.parseColor("#1A5D1A"))
            }
            "3"-> {
                binding.status.text = "Telah Selesai"
                binding.status.setBackgroundColor(Color.parseColor("#1A5D1A"))
            }
            "4"-> {
                binding.status.text = "Ditolak"
                binding.status.setBackgroundColor(Color.parseColor("#FF0000"))
            }

        }

        with(dataPengaduan){
            binding.nama.text = nama
            binding.telp.text = telp
            binding.judulpengaduan.text = judulpengaduan
            binding.isipengaduan.text = isipengaduan
            binding.lokasiId.text = datalokasi
            binding.kecamatanId.text = namakecamatan
            binding.kelurahanId.text = namakelurahan
            binding.bidangId.text = namabidang

            Glide
                .with(this@DetailPengaduanActivity)
                .load("https://pupr.hstkab.go.id/elapor/elapor/public/foto/" +foto)
                .centerCrop()
                .into(binding.iv)

        }

    }
}