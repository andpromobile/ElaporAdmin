package com.example.elaporadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.elaporadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var btnBidang: Button
    private lateinit var btnKelurahan:Button
    private lateinit var btnPerangkatDesa:Button
    private lateinit var btnPegawai:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadBidang()
        loadKelurahan()
        loadPerangkatDesa()
        loadPegawai()

    }

    private fun loadPegawai() {
        btnPegawai = binding.toPegawai

        btnPegawai.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                PegawaiActivity::class.java
            )

            startActivity(intent)
        }
    }

    private fun loadPerangkatDesa() {
        btnPerangkatDesa = binding.toPerangkatDesa

        btnPerangkatDesa.setOnClickListener {
            val intent = Intent(this@MainActivity, PerangkatDesaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadKelurahan() {
        btnKelurahan = binding.toKelurahan

        btnKelurahan.setOnClickListener{
            val intent = Intent(this@MainActivity, KelurahanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadBidang() {
        btnBidang = binding.toBidang

        btnBidang.setOnClickListener{
            val intent = Intent(this@MainActivity, BidangActivity::class.java)
            startActivity(intent)
        }
    }
}