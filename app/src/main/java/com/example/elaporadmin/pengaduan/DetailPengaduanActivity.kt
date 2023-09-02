package com.example.elaporadmin.pengaduan

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.elaporadmin.databinding.ActivityDetailPengaduanBinding

class DetailPengaduanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPengaduanBinding
    private lateinit var dataPengaduan:Pengaduan
    private var sumber = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPengaduanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetailPengaduan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val pengaduanViewModel = ViewModelProvider(this)[PengaduanViewModel::class.java]
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

            sumber = intent.getStringExtra("SUMBER").toString()
        }

        when(sumber){
            "1"->{
                when(dataPengaduan.status){
                    "0" -> {
                        binding.status.text = "Menunggu Verifikasi Perangkat Desa"
                        binding.status.setTextColor(Color.parseColor("#900C3F"))
                    }
                    "1"-> {
                        binding.status.text = "Telah Diverifikasi Perangkat Desa"
                        binding.status.setTextColor(Color.parseColor("#F94C10"))

                        binding.btnVerifikasiPengaduanPegawai.visibility = View.VISIBLE
                        binding.btnVerifikasiPengaduanPegawai.text = "Verifikasi"
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

                if(dataPengaduan.status == "1"){
                    dataPengaduan.id?.let { it1 -> pengaduanViewModel.pengaduanVerifikasi(it1) }
                }else if(dataPengaduan.status == "2"){
                    dataPengaduan.id?.let { it1 -> pengaduanViewModel.pengaduanVerifikasi1(it1) }
                }
            }
            "2"->{
                when(dataPengaduan.status){
                    "0" -> {
                        binding.status.text = "Pending"
                        binding.status.setTextColor(Color.parseColor("#900C3F"))
                        binding.btnVerifikasiPengaduanPegawai.visibility = View.VISIBLE
                        binding.btnVerifikasiPengaduanPegawai.text = "Verifikasi"
                    }
                    "1"-> {
                        binding.status.text = "Terverifikasi"
                        binding.status.setTextColor(Color.parseColor("#F94C10"))
                    }
                    "2"-> {
                        binding.status.text = "Diverifikasi Kepala Bidang"
                        binding.status.setTextColor(Color.parseColor("#1A5D1A"))
                    }
                    "3"-> {
                        binding.status.text = "Telah Selesai"
                        binding.status.setTextColor(Color.parseColor("#1A5D1A"))
                        binding.btnTolakPengaduanPegawai.visibility = View.GONE
                    }
                    "4"-> {
                        binding.status.text ="Ditolak"
                        binding.status.setTextColor(Color.parseColor("#FF0000"))
                        binding.btnTolakPengaduanPegawai.visibility = View.GONE
                    }
                }

                if(dataPengaduan.status == "0"){
                    dataPengaduan.id?.let { it1 -> pengaduanViewModel.pengaduanVerifikasiPd(it1) }
                }
            }
        }

//        when(dataPengaduan.status){
//            "0" -> {
//                binding.status.text = "PENDING"
//                binding.status.setBackgroundColor(Color.parseColor("#900C3F"))
//            }
//            "1"-> {
//                binding.status.text = "Diteruskan Ke Kepala Bidang"
//                binding.status.setBackgroundColor(Color.parseColor("#F94C10"))
//            }
//            "2"-> {
//                binding.status.text = "Telah Diverifikasi Kepala Bidang"
//                binding.status.setBackgroundColor(Color.parseColor("#1A5D1A"))
//            }
//            "3"-> {
//                binding.status.text = "Telah Selesai"
//                binding.status.setBackgroundColor(Color.parseColor("#1A5D1A"))
//            }
//            "4"-> {
//                binding.status.text = "Ditolak"
//                binding.status.setBackgroundColor(Color.parseColor("#FF0000"))
//            }
//
//        }

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