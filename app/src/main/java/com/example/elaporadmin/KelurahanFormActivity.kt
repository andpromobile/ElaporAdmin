package com.example.elaporadmin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.elaporadmin.databinding.ActivityKelurahanFormBinding

class KelurahanFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKelurahanFormBinding
    private lateinit var btnFormBinding:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelurahanFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnFormBinding = binding.btnFormKelurahan

        btnFormBinding.setOnClickListener {
            finish()
        }
    }
}