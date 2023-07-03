package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.ViewModel.LokasiViewModel
import com.example.elaporadmin.adapter.ListLokasiAdapter
import com.example.elaporadmin.adapter.ListLokasiAdapter.OnAdapterListener
import com.example.elaporadmin.dao.Lokasi
import com.example.elaporadmin.databinding.ActivityLokasiBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LokasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLokasiBinding
    private lateinit var listLokasiAdapter: ListLokasiAdapter
    private lateinit var rvLokasi: RecyclerView
    private lateinit var fabLokasi: FloatingActionButton
    private lateinit var tvNoLokasi: TextView
    private lateinit var lokasiViewModel: LokasiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormLokasi()
    }

    private fun initLayout() {
        rvLokasi.apply {
            binding.listLokasi
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }

        lokasiViewModel = ViewModelProvider(this)[LokasiViewModel::class.java]

        lokasiViewModel.getLokasi()
        lokasiViewModel.observeLokasiLiveData().observe(
            this
        ){lokasiList->
            listLokasiAdapter = ListLokasiAdapter(
                lokasiList as ArrayList<Lokasi>,
                object: OnAdapterListener {
                    override fun onClick(lokasi:Lokasi){
                        val intent = Intent(
                            this@LokasiActivity,
                            LokasiFormActivity::class.java
                        )
                        startActivity(intent)
                    }
                }
            )

            rvLokasi.adapter = listLokasiAdapter

            if (listLokasiAdapter.itemCount <= 0){
                rvLokasi.visibility = View.GONE
                tvNoLokasi.visibility = View.VISIBLE
            }
        }

    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun toFormLokasi() {
        fabLokasi = binding.fabLokasi

        fabLokasi.setOnClickListener {
            val intent = Intent(this@LokasiActivity, LokasiFormActivity::class.java)
            startActivity(intent)
        }
    }


}