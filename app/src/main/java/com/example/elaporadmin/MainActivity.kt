package com.example.elaporadmin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.elaporadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var btnLogin:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnLogin = binding.btnLogin

        btnLogin.setOnClickListener {
            val loginFragment = LoginFragment()
            loginFragment.show(supportFragmentManager, loginFragment.tag)
        }
    }


}