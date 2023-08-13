package com.example.elaporadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.elaporadmin.databinding.ActivityPegawaiDashboardBinding

class PegawaiDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPegawaiDashboardBinding
    private lateinit var nav1:LinearLayout
    private lateinit var nav2:LinearLayout
    private lateinit var nav3:LinearLayout
    private var nik:String = ""
    private var nama:String = ""
    private var bidang_id:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPegawaiDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.nav1.setOnClickListener {
            loadFragment(PegawaiDashboardFragment1())
        }

        binding.nav2.setOnClickListener {
            loadFragment(PegawaiDashboardFragment2())
        }

        binding.nav3.setOnClickListener {
            loadFragment(PegawaiDashboardFragment3())
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }
}