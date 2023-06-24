package com.example.elaporadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.databinding.ActivityBidangBinding
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BidangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidangBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidangBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val rvBidang = binding.listBidang
        rvBidang.setHasFixedSize(true)

    }

    override fun onStart() {
        super.onStart()
        getDataBidang()
    }

    fun getDataBidang(){
        ApiService.endPoint.getBidang()
            .enqueue(object :Callback<List<Bidang>>{
                override fun onResponse(
                    call: Call<List<Bidang>>?,
                    response: Response<List<Bidang>>?
                ) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<List<Bidang>>?, t: Throwable?) {
                    TODO("Not yet implemented")
                }

            })
    }
}