package com.example.elaporadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.elaporadmin.databinding.ActivityBidangFormBinding

class BidangFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidangFormBinding
    private lateinit var btnFormBinding: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidangFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnFormBinding = binding.btnFormBidang

        btnFormBinding.setOnClickListener {
            finish()
        }
    }
}