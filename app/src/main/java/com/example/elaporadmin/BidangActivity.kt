package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.adapter.ListBidangAdapter
import com.example.elaporadmin.adapter.ListBidangAdapter.OnAdapterListener
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.databinding.ActivityBidangBinding
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BidangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidangBinding
    private lateinit var listBidangAdapter: ListBidangAdapter
    private lateinit var rvBidang:RecyclerView
    private lateinit var fabBidang:FloatingActionButton
    private lateinit var tvNoBidang: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidangBinding.inflate(layoutInflater)

        setContentView(binding.root)


        rvBidang = binding.listBidang
        rvBidang.setHasFixedSize(true)
        rvBidang.layoutManager = LinearLayoutManager(this)


        getDataBidang()
        toFormBidang()
    }

    override fun onStart() {
        super.onStart()
        getDataBidang()
    }

    fun toFormBidang(){
        fabBidang = binding.fabBidang

        fabBidang.setOnClickListener {
            val intent = Intent(this@BidangActivity,
                BidangFormActivity::class.java)
            startActivity(intent)
        }
    }

    fun getDataBidang(){
        ApiService.endPoint.getBidang()
            .enqueue(
                object : Callback<List<Bidang>> {
                    override fun onResponse(
                        call: Call<List<Bidang>>,
                        response: Response<List<Bidang>>,
                    ) {
                        listBidangAdapter = ListBidangAdapter(
                            response.body() as ArrayList<Bidang>,
                            object : OnAdapterListener {
                                override fun onClick(bidang: Bidang) {
                                    val intent = Intent(
                                        this@BidangActivity,
                                        BidangFormActivity::class.java,
                                    )
                                    startActivity(intent)
                                }
                            },
                        )
                        rvBidang.adapter = listBidangAdapter

                        if (listBidangAdapter.itemCount <= 0){
                            rvBidang.visibility = View.GONE
                            tvNoBidang.visibility = View.VISIBLE
                        }
                    }

                    override fun onFailure(call: Call<List<Bidang>>, t: Throwable) {
                        Toast.makeText(applicationContext,
                            "Koneksi ke Server Gagal!!!",
                            Toast.LENGTH_SHORT).show()
                    }

                },
            )
    }
}