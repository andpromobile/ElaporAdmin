package com.example.elaporadmin.pegawai

import android.os.Bundle
import android.util.Log
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
    private var bidang_id:Int = 0
    private var mBundle = Bundle()
    private var mBundleProfile = Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPegawaiDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbar_bottom = binding.navbarBottom

        if (!intent.extras?.isEmpty!!){

            nik = intent.getStringExtra("NIK")!!.toString()
            nama = intent.getStringExtra("NAMA").toString()
            bidang_id = intent.getIntExtra("BIDANGID",0)

            mBundleProfile.putString("NIK",nik)
            binding.tv2.setText(nama)
        }
        Log.d("Dari Dashboard", bidang_id.toString())
        mBundle.putInt("BIDANGID", bidang_id)
        loadFragment(PegawaiDashboardFragment1(), mBundle)

        setListener()
    }

    private fun setListener() {
        binding.btnLogOut.setOnClickListener {
            finish()
        }

        navbar_bottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav1 -> {
                    loadFragment(PegawaiDashboardFragment1(),mBundle)
                    true
                }
                R.id.nav2 -> {
                    loadFragment(PegawaiDashboardFragment2(),mBundleProfile)
                    true
                }
//                R.id.nav3 -> {
//                    loadFragment(PegawaiDashboardFragment3())
//                    true
//                }

                else -> {
                    false
                }
            }
        }
    }

    private  fun loadFragment(fragment: Fragment, bundle: Bundle = Bundle()){
        val transaction = supportFragmentManager.beginTransaction()
        if (!bundle.isEmpty) fragment.arguments = bundle
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