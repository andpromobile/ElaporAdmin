package com.example.elaporadmin.retrofit

import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.dao.Kelurahan
import com.example.elaporadmin.dao.Lokasi
import com.example.elaporadmin.dao.Pegawai
import com.example.elaporadmin.dao.Pengaduan
import com.example.elaporadmin.dao.Perangkatdesa
import com.example.elaporadmin.dao.ResponseBidang
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndPoint {

    @GET("bidang.php")
    fun getBidang(): Call<ResponseBidang>

    @FormUrlEncoded
    @POST("bidang-insert.php")
    fun insertBidang(
        @Field("namabidang") namabidang:String,
        @Field("seksi") seksi:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("bidang-update.php")
    fun updateBidang(
        @Field("id") id:Int,
        @Field("namabidang") namabidang:String,
        @Field("seksi") seksi:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("bidang-delete.php")
    fun deleteBidang(
        @Field("id") id:Int,
    ): Call<SubmitModel>


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