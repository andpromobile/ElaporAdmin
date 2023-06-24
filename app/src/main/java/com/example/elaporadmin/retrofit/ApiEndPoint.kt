package com.example.elaporadmin.retrofit

import com.example.elaporadmin.dao.Bidang
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoint {

    @GET("bidang")
    fun getBidang(): Call<List<Bidang>>
}