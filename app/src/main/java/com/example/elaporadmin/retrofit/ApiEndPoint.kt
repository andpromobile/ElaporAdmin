package com.example.elaporadmin.retrofit

import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.dao.Kelurahan
import com.example.elaporadmin.dao.Lokasi
import com.example.elaporadmin.dao.Pegawai
import com.example.elaporadmin.dao.Pengaduan
import com.example.elaporadmin.dao.Perangkatdesa
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoint {

    @GET("bidang.php")
    fun getBidang(): Call<List<Bidang>>

    @GET("kelurahan.php")
    fun getKelurahan(): Call<List<Kelurahan>>

    @GET("lokasi.php")
    fun getLokasi(): Call<List<Lokasi>>

    @GET("pegawai.php")
    fun getPegawai(): Call<List<Pegawai>>

    @GET("pengaduan.php")
    fun getPengaduan(): Call<List<Pengaduan>>

    @GET("perangkatdesa.php")
    fun getPerangkatDesa(): Call<List<Perangkatdesa>>
}