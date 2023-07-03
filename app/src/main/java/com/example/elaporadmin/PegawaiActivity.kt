package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.ViewModel.PegawaiViewModel
import com.example.elaporadmin.adapter.ListLokasiAdapter
import com.example.elaporadmin.adapter.ListPegawaiAdapter
import com.example.elaporadmin.dao.Lokasi
import com.example.elaporadmin.dao.Pegawai
import com.example.elaporadmin.databinding.ActivityPegawaiBinding
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PegawaiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPegawaiBinding
    private lateinit var listPegawaiAdapter: ListPegawaiAdapter
    private lateinit var rvPegawai: RecyclerView
    private lateinit var fabPegawai: FloatingActionButton
    private lateinit var tvNoPegawai: TextView
    private lateinit var pegawaiViewModel: PegawaiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPegawaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormPegawai()
    }

    private fun initLayout() {
        rvPegawai.apply {
            binding.listPegawai
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }

        pegawaiViewModel = ViewModelProvider(this)[PegawaiViewModel::class.java]
        pegawaiViewModel.getPegawai()
        pegawaiViewModel.observePegawaiLiveData().observe(
            this
        ){ pegawaiList ->
            listPegawaiAdapter = ListPegawaiAdapter(
                pegawaiList as ArrayList<Pegawai>,
                object : ListPegawaiAdapter.OnAdapterListener{
                    override fun onClick(pegawai: Pegawai) {
                        val intent = Intent(
                            this@PegawaiActivity,
                            PegawaiFormActivity::class.java
                        )

                        startActivity(intent)
                    }

                }
            )

            rvPegawai.adapter = listPegawaiAdapter

            if(listPegawaiAdapter.itemCount <= 0){
                rvPegawai.visibility = View.GONE
                tvNoPegawai.visibility = View.VISIBLE
            }

        }
    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun toFormPegawai() {
        fabPegawai = binding.fabPegawai

        fabPegawai.setOnClickListener {
            val intent = Intent(this@PegawaiActivity, PegawaiFormActivity::class.java)
            startActivity(intent)
        }
    }

}