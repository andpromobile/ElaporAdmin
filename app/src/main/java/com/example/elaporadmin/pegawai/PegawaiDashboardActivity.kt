package com.example.elaporadmin.pegawai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.R
import com.example.elaporadmin.databinding.ActivityPegawaiDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class PegawaiDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPegawaiDashboardBinding
    private lateinit var navbar_bottom: BottomNavigationView
    private var nik:String = ""
    private var nama:String = ""
    private var bidang_id:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPegawaiDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbar_bottom = binding.navbarBottom

        if (!intent.extras?.isEmpty!!){

            nik = intent.getStringExtra("NIK")!!.toString()
            nama = intent.getStringExtra("NAMA").toString()
//            bidang_id = intent.getStringExtra("BIDANG_ID")!!.toString()

            binding.tv2.setText(nama)
        }

        loadFragment(PegawaiDashboardFragment1())

        setListener()
    }

    private fun setListener() {
        binding.btnLogOut.setOnClickListener {
            finish()
        }

        navbar_bottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav1 -> {
                    loadFragment(PegawaiDashboardFragment1())
                    true
                }
                R.id.nav2 -> {
                    loadFragment(PegawaiDashboardFragment2())
                    true
                }
                R.id.nav3 -> {
                    loadFragment(PegawaiDashboardFragment3())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setContentText("Apakah Anda Ingin Keluar Dari Aplikasi?")
            .setConfirmButton("IYA") {
                finish()
            }
            .show()

    }
}