package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.adapter.ListKelurahanAdapter
import com.example.elaporadmin.dao.Kelurahan
import com.example.elaporadmin.databinding.ActivityKelurahanBinding
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelurahanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityKelurahanBinding
    private lateinit var listKelurahanAdapter: ListKelurahanAdapter
    private lateinit var rvKelurahan:RecyclerView
    private lateinit var fabKelurahan:FloatingActionButton
    private lateinit var tvNoKelurahan:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelurahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inisialiasi()
        getDataKelurahan()
        toFormKelurahan()

    }

    private fun toFormKelurahan() {
        fabKelurahan = binding.fabKelurahan

        fabKelurahan.setOnClickListener {
            val intent = Intent(this@KelurahanActivity, KelurahanFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDataKelurahan() {
        ApiService.endPoint.getKelurahan().enqueue(object : Callback<List<Kelurahan>>{
            override fun onResponse(
                call: Call<List<Kelurahan>>,
                response: Response<List<Kelurahan>>
            ) {

                listKelurahanAdapter = ListKelurahanAdapter(
                    response.body() as ArrayList<Kelurahan>,
                    object : ListKelurahanAdapter.OnAdapterListener {
                        override fun onClick(kelurahan:Kelurahan) {
                            val intent = Intent(
                                this@KelurahanActivity,
                                KelurahanFormActivity::class.java,
                            )
                            startActivity(intent)
                        }
                    },
                )
                rvKelurahan.adapter = listKelurahanAdapter

                if (listKelurahanAdapter.itemCount <= 0){
                    rvKelurahan.visibility = View.GONE
                    tvNoKelurahan.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<Kelurahan>>, t: Throwable) {
                Log.d("tes", t.toString())
            }

        })
    }

    private fun inisialiasi() {
        tvNoKelurahan = binding.noKelurahan

        rvKelurahan = binding.listKelurahan
        rvKelurahan.setHasFixedSize(true)
        rvKelurahan.layoutManager = LinearLayoutManager(this)
    }
}