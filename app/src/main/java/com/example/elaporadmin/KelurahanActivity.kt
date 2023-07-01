package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.ViewModel.KelurahanViewModel
import com.example.elaporadmin.adapter.ListKelurahanAdapter
import com.example.elaporadmin.dao.Kelurahan
import com.example.elaporadmin.databinding.ActivityKelurahanBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class KelurahanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityKelurahanBinding
    private lateinit var listKelurahanAdapter: ListKelurahanAdapter
    private lateinit var rvKelurahan:RecyclerView
    private lateinit var fabKelurahan:FloatingActionButton
    private lateinit var tvNoKelurahan:TextView
    private lateinit var kelurahanViewModel: KelurahanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelurahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormKelurahan()

    }

    private fun initLayout() {
        tvNoKelurahan = binding.noKelurahan

        rvKelurahan = binding.listKelurahan

        rvKelurahan.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }


        kelurahanViewModel = ViewModelProvider(this)[KelurahanViewModel::class.java]
        kelurahanViewModel.apply {
            getKelurahan()
            observeKelurahanLiveData().observe(
                this@KelurahanActivity
            ) { kelurahanList ->
                listKelurahanAdapter = ListKelurahanAdapter(
                    kelurahanList as ArrayList<Kelurahan>,
                    object : ListKelurahanAdapter.OnAdapterListener {
                        override fun onClick(kelurahan: Kelurahan) {
                            val intent = Intent(
                                this@KelurahanActivity,
                                KelurahanFormActivity::class.java,
                            )
                            startActivity(intent)
                        }
                    },
                )
                rvKelurahan.adapter = listKelurahanAdapter

                if (listKelurahanAdapter.itemCount <= 0) {
                    rvKelurahan.visibility = View.GONE
                    tvNoKelurahan.visibility = View.VISIBLE
                }
            }
        }


    }

    private fun toFormKelurahan() {
        fabKelurahan = binding.fabKelurahan

        fabKelurahan.setOnClickListener {
            val intent = Intent(this@KelurahanActivity, KelurahanFormActivity::class.java)
            startActivity(intent)
        }
    }

}