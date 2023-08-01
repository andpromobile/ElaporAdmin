package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.elaporadmin.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var btnBidang: CardView
    private lateinit var btnKelurahan: CardView
    private lateinit var btnPerangkatDesa: CardView
    private lateinit var btnPegawai: CardView
//    private lateinit var btnPengaduan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadBidang()
        loadKelurahan()
        loadPerangkatDesa()
        loadPegawai()
//        loadPengaduan()
    }

//    private fun loadPengaduan() {
//        btnPengaduan = binding.toPengaduan
//
//        btnPengaduan.setOnClickListener {
//            val intent = Intent(
//                this@DashboardActivity,
//                PengaduanFormActivity::class.java
//            )
//
//            startActivity(intent)
//        }
//
//
//    }

    private fun loadPegawai() {
        btnPegawai = binding.toPegawai

        btnPegawai.setOnClickListener {
            val intent = Intent(
                this@DashboardActivity,
                PegawaiActivity::class.java
            )

            startActivity(intent)
        }
    }

    private fun loadPerangkatDesa() {
        btnPerangkatDesa = binding.toPerangkatDesa

        btnPerangkatDesa.setOnClickListener {
            val intent = Intent(this@DashboardActivity, PerangkatDesaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadKelurahan() {
        btnKelurahan = binding.toKelurahan

        btnKelurahan.setOnClickListener{
            val intent = Intent(this@DashboardActivity, KelurahanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadBidang() {
        btnBidang = binding.toBidang

        btnBidang.setOnClickListener{
            val intent = Intent(this@DashboardActivity, BidangActivity::class.java)
            startActivity(intent)
        }
    }
}