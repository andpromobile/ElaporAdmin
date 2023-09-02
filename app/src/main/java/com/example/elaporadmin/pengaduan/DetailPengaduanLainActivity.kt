package com.example.elaporadmin.pengaduan

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.example.elaporadmin.databinding.ActivityDetailPengaduanLainBinding

class DetailPengaduanLainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPengaduanLainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPengaduanLainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetailPengaduanLain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val pengaduanLainViewModel = ViewModelProvider(this)[PengaduanLainViewModel::class.java]

        if (!intent.extras?.isEmpty!!){
//            val id = intent.getIntExtra("ID",0)
            binding.namaLain.text = intent.getStringExtra("NAMA").toString()
            binding.telpLain.text = intent.getStringExtra("TELP").toString()
            binding.judulpengaduanlain.text = intent.getStringExtra("JUDUL").toString()
            binding.isipengaduanlain.text = intent.getStringExtra("ISI").toString()
            binding.lokasiId.text = intent.getStringExtra("LOKASI").toString()
            binding.kelurahanId.text = intent.getStringExtra("KELURAHAN").toString()
//            binding. = intent.getStringExtra("TANGGAL").toString()

            when(intent.getStringExtra("STATUS").toString()){
                "0" -> {
                    binding.status.text = "Pending"
                    binding.status.setTextColor(Color.parseColor("#900C3F"))
                    binding.btnTolakPengaduanPegawai.visibility = View.GONE
                    binding.btnVerifikasiPengaduanPegawai.visibility = View.VISIBLE
                    binding.btnVerifikasiPengaduanPegawai.text = "Verifikasi"
                }
                "1"-> {
                    binding.status.text = "Di Teruskan Ke Pihak Berwenang"
                    binding.status.setTextColor(Color.parseColor("#F94C10"))

                    binding.btnTolakPengaduanPegawai.visibility = View.GONE
                    binding.btnVerifikasiPengaduanPegawai.visibility = View.GONE

                }
                "2"-> {
                    binding.status.text = "Diverifikasi"
                    binding.status.setTextColor(Color.parseColor("#1A5D1A"))

                    binding.btnVerifikasiPengaduanPegawai.visibility = View.VISIBLE
                    binding.btnVerifikasiPengaduanPegawai.text = "Selesai"
                }
                "3"-> {
                    binding.status.text = "Telah Selesai"
                    binding.status.setTextColor(Color.parseColor("#1A5D1A"))

                    binding.btnTolakPengaduanPegawai.visibility = View.GONE
                }
                "4"-> {
                    binding.status.text ="Ditolak oleh Perangkat Desa"
                    binding.status.setTextColor(Color.parseColor("#FF0000"))
                    binding.btnTolakPengaduanPegawai.visibility = View.GONE
                }
            }

//            binding.status.text = intent.getStringExtra("STATUS").toString()
            binding.latitude.text = intent.getStringExtra("LATITUDE").toString()
            binding.longitude.text = intent.getStringExtra("LONGITUDE").toString()

            val foto = intent.getStringExtra("FOTO")

            Glide
                .with(this@DetailPengaduanLainActivity)
                .load("https://pupr.hstkab.go.id/elapor/elapor/public/foto/" +foto)
                .centerCrop()
                .into(binding.ivLain)

            binding.btnVerifikasiPengaduanPegawai.setOnClickListener {
                pengaduanLainViewModel.verifikasi(intent.getIntExtra("ID",0))

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Pengaduan Di Verifikasi")
                    .show()
            }

        }


    }

}