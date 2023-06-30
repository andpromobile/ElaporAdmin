package com.example.elaporadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.adapter.ListBidangAdapter
import com.example.elaporadmin.adapter.ListLokasiAdapter
import com.example.elaporadmin.dao.Lokasi
import com.example.elaporadmin.databinding.ActivityBidangBinding
import com.example.elaporadmin.databinding.ActivityLokasiBinding
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LokasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLokasiBinding
    private lateinit var listLokasiAdapter: ListLokasiAdapter
    private lateinit var rvLokasi: RecyclerView
    private lateinit var fabLokasi: FloatingActionButton
    private lateinit var tvNoLokasi: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inisialiasi()
        getDataLokasi()
        toFormLokasi()
    }

    private fun toFormLokasi() {
        fabLokasi = binding.fabLokasi

        fabLokasi.setOnClickListener {
            val intent = Intent(this@LokasiActivity, LokasiFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDataLokasi() {
        ApiService.endPoint.getLokasi().enqueue(object : Callback<List<Lokasi>> {
            override fun onResponse(call: Call<List<Lokasi>>, response: Response<List<Lokasi>>) {
                listLokasiAdapter = ListLokasiAdapter(
                    response.body() as ArrayList<Lokasi>,
                    object:ListLokasiAdapter.OnAdapterListener{
                        override fun onClick(lokasi: Lokasi) {
                            val intent = Intent(
                                this@LokasiActivity,
                                LokasiFormActivity::class.java,
                            )
                            startActivity(intent)
                        }
                    },
                )

                rvLokasi.adapter = listLokasiAdapter

                if (listLokasiAdapter.itemCount <= 0){
                    rvLokasi.visibility = View.GONE
                    tvNoLokasi.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<Lokasi>>, t: Throwable) {
                Toast.makeText(applicationContext,
                    "Koneksi ke Server Gagal!!!",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun inisialiasi() {
        tvNoLokasi = binding.noLokasi

        rvLokasi = binding.listLokasi
        rvLokasi.setHasFixedSize(true)
        rvLokasi.layoutManager = LinearLayoutManager(this)
    }
}