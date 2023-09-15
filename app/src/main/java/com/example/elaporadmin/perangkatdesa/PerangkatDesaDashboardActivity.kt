package com.example.elaporadmin.perangkatdesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.R
import com.example.elaporadmin.databinding.ActivityPerangkatDesaDashboardBinding
import com.example.elaporadmin.pegawai.PegawaiDashboardFragment1
import com.example.elaporadmin.pegawai.PegawaiDashboardFragment2
import com.example.elaporadmin.pegawai.PegawaiDashboardFragment3
import com.google.android.material.bottomnavigation.BottomNavigationView

class PerangkatDesaDashboardActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPerangkatDesaDashboardBinding
    private lateinit var navbar_bottom:BottomNavigationView
    private var nik:String = ""
    private var nama:String = ""
    private var bidang_id:Int = 0
    private var kecamatan_id:Int = 0
    private var kelurahan_id:Int = 0
    private val mBundle = Bundle()
    private val mBundleProfile = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerangkatDesaDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbar_bottom = binding.navbarBottom

        if (!intent.extras?.isEmpty!!){

            nik = intent.getStringExtra("NIK")!!.toString()
            nama = intent.getStringExtra("NAMA").toString()
            bidang_id = intent.getIntExtra("BIDANGID", 0)
            kecamatan_id = intent.getIntExtra("KECAMATANID", 0)
            kelurahan_id = intent.getIntExtra("KELURAHANID", 0)

            mBundleProfile.putString("NIK",nik)

            binding.tv2.setText(nama)
        }

        mBundle.putInt("KECAMATANID",kecamatan_id)
        mBundle.putInt("KELURAHANID",kelurahan_id)

        loadFragment(PerangkatDesaFragment1(), mBundle)

        setListener()
    }

    private fun setListener() {
        binding.btnLogOut.setOnClickListener {
            finish()
        }

        navbar_bottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navpd1 -> {

                    loadFragment(PerangkatDesaFragment1(), mBundle)
                    true
                }
                R.id.navpd2 -> {
                    loadFragment(PerangkatDesaFragment2(), mBundleProfile)
                    true
                }
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