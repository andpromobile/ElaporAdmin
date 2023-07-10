package com.example.elaporadmin.retrofit

import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.dao.ResponseBidang
import com.example.elaporadmin.dao.ResponseKelurahan
import com.example.elaporadmin.dao.ResponseLokasi
import com.example.elaporadmin.dao.ResponsePegawai
import com.example.elaporadmin.dao.ResponsePengaduan
import com.example.elaporadmin.dao.ResponsePerangkatdesa
import retrofit2.Call
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
    fun getKelurahan(): Call<ResponseKelurahan>

    @GET("lokasi.php")
    fun getLokasi(): Call<ResponseLokasi>

    @GET("pegawai.php")
    fun getPegawai(): Call<ResponsePegawai>

    @GET("pengaduan.php")
    fun getPengaduan(): Call<ResponsePengaduan>

    @GET("perangkatdesa.php")
    fun getPerangkatDesa(): Call<ResponsePerangkatdesa>
}