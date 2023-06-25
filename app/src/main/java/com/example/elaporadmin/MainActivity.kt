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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadBidang()
        loadKelurahan()

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