package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.adapter.ListPerangkatDesaAdapter
import com.example.elaporadmin.dao.Perangkatdesa
import com.example.elaporadmin.databinding.ActivityPerangkatDesaBinding
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerangkatDesaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerangkatDesaBinding
    private lateinit var listPerangkatDesaAdapter: ListPerangkatDesaAdapter
    private lateinit var rvPerangkatdesa: RecyclerView
    private lateinit var fabPerangkatDesa: FloatingActionButton
    private lateinit var tvNoPerangkatDesa: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerangkatDesaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inisialiasi()
        getDataPerangkatDesa()
        toFormPerangkatDesa()
    }

    private fun getDataPerangkatDesa() {
        ApiService.endPoint.getPerangkatDesa().enqueue(object: Callback<List<Perangkatdesa>> {
            override fun onResponse(
                call: Call<List<Perangkatdesa>>,
                response: Response<List<Perangkatdesa>>
            ) {
                listPerangkatDesaAdapter = ListPerangkatDesaAdapter(
                    response.body() as ArrayList<Perangkatdesa>,
                    object : ListPerangkatDesaAdapter.OnAdapterListener {
                        override fun onClick(perangkatDesa: Perangkatdesa) {
                            val intent = Intent(
                                this@PerangkatDesaActivity,
                                PerangkatDesaFormActivity::class.java,
                            )
                            startActivity(intent)
                        }
                    },
                )
                rvPerangkatdesa.adapter = listPerangkatDesaAdapter

                if (listPerangkatDesaAdapter.itemCount <= 0){
                    rvPerangkatdesa.visibility = View.GONE
                    tvNoPerangkatDesa.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<Perangkatdesa>>, t: Throwable) {
                Toast.makeText(applicationContext,
                    "Koneksi ke Server Gagal!!!",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun toFormPerangkatDesa() {
        fabPerangkatDesa = binding.fabPerangkatDesa

        fabPerangkatDesa.setOnClickListener {
            val intent = Intent(this@PerangkatDesaActivity, PerangkatDesaFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inisialiasi() {
        tvNoPerangkatDesa = binding.noPerangkatDesa

        rvPerangkatdesa = binding.listPerangkatDesa
        rvPerangkatdesa.setHasFixedSize(true)
        rvPerangkatdesa.layoutManager = LinearLayoutManager(this)

    }
}