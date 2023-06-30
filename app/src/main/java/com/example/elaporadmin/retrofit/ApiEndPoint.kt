package com.example.elaporadmin.retrofit

import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.dao.Kelurahan
import com.example.elaporadmin.dao.Lokasi
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoint {

    @GET("bidang.php")
    fun getBidang(): Call<List<Bidang>>

    @GET("kelurahan.php")
    fun getKelurahan(): Call<List<Kelurahan>>

    @GET("lokasi.php")
    fun getLokasi(): Call<List<Lokasi>>
}