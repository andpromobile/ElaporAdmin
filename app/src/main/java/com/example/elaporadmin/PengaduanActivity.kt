package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.adapter.ListPengaduanAdapter
import com.example.elaporadmin.dao.Pengaduan
import com.example.elaporadmin.databinding.ActivityPengaduanBinding
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengaduanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengaduanBinding
    private lateinit var listPengaduanAdapter: ListPengaduanAdapter
    private lateinit var rvPengaduan: RecyclerView
    private lateinit var fabPengaduan: FloatingActionButton
    private lateinit var tvNoPengaduan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengaduanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        inisialiasi()
        getDataPengaduan()
        toFormPengaduan()
    }

    private fun getDataPengaduan() {
        ApiService.endPoint.getPengaduan().enqueue(
            object :Callback<List<Pengaduan>>{
                override fun onResponse(
                    call: Call<List<Pengaduan>>,
                    response: Response<List<Pengaduan>>
                ) {
                    listPengaduanAdapter = ListPengaduanAdapter(
                        response.body() as ArrayList<Pengaduan>,
                        object : ListPengaduanAdapter.OnAdapterListener {
                            override fun onClick(pengaduan: Pengaduan) {
                                val intent = Intent(
                                    this@PengaduanActivity,
                                    PengaduanFormActivity::class.java,
                                )
                                startActivity(intent)
                            }
                        },
                    )
                    rvPengaduan.adapter = listPengaduanAdapter

                    if (listPengaduanAdapter.itemCount <= 0){
                        rvPengaduan.visibility = View.GONE
                        tvNoPengaduan.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<List<Pengaduan>>, t: Throwable) {
                    Toast.makeText(applicationContext,
                        "Koneksi ke Server Gagal!!!",
                        Toast.LENGTH_SHORT).show()
                }

            }
        )
    }

    private fun toFormPengaduan() {
        fabPengaduan = binding.fabPengaduan

        fabPengaduan.setOnClickListener {
            val intent = Intent(this@PengaduanActivity, PengaduanFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inisialiasi() {
        tvNoPengaduan = binding.noPengaduan

        rvPengaduan = binding.listPengaduan
        rvPengaduan.setHasFixedSize(true)
        rvPengaduan.layoutManager = LinearLayoutManager(this)

    }
}