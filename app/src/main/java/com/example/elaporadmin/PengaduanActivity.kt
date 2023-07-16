package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.ViewModel.PengaduanViewModel
import com.example.elaporadmin.adapter.ListPengaduanAdapter
import com.example.elaporadmin.dao.Pengaduan
import com.example.elaporadmin.databinding.ActivityPengaduanBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PengaduanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengaduanBinding
    private lateinit var listPengaduanAdapter: ListPengaduanAdapter
    private lateinit var rvPengaduan: RecyclerView
    private lateinit var fabPengaduan: FloatingActionButton
    private lateinit var tvNoPengaduan: TextView
    private lateinit var pengaduanViewModel: PengaduanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengaduanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initLayout()
        toFormPengaduan()
    }

    private fun initLayout() {
        rvPengaduan.apply {
            binding.listPengaduan
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }

        pengaduanViewModel = ViewModelProvider(this)[PengaduanViewModel::class.java]
        pengaduanViewModel.getPengaduan()
        pengaduanViewModel.observePengaduanLiveData().observe(
            this
        ){pengaduanList->
            listPengaduanAdapter = ListPengaduanAdapter(
                pengaduanList as ArrayList<Pengaduan>,
                object :ListPengaduanAdapter.OnAdapterListener{
                    override fun onClick(pengaduan: Pengaduan) {
                        val intent = Intent(
                            this@PengaduanActivity,
                            PengaduanFormActivity::class.java
                        )
                        startActivity(intent)
                    }

                }
            )

            rvPengaduan.adapter = listPengaduanAdapter

            if (listPengaduanAdapter.itemCount <= 0){
                rvPengaduan.visibility = View.GONE
                tvNoPengaduan.visibility = View.VISIBLE
            }else{
                rvPengaduan.visibility = View.VISIBLE
                tvNoPengaduan.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun toFormPengaduan() {
        fabPengaduan = binding.fabPengaduan

        fabPengaduan.setOnClickListener {
            val intent = Intent(this@PengaduanActivity, PengaduanFormActivity::class.java)
            startActivity(intent)
        }
    }

}