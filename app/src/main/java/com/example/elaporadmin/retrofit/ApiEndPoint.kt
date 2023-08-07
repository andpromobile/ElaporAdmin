package com.example.elaporadmin.retrofit

import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.dao.ResponseBidang
import com.example.elaporadmin.dao.ResponseKelurahan
import com.example.elaporadmin.dao.ResponseLokasi
import com.example.elaporadmin.dao.ResponsePegawai
import com.example.elaporadmin.dao.ResponsePengaduan
import com.example.elaporadmin.dao.ResponsePerangkatdesa
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiEndPoint {

    @GET("dtxbidang")
    fun getBidang(): Call<ResponseBidang>

    @FormUrlEncoded
    @POST("dtxbidang/store")
    fun insertBidang(
        @Field("namabidang") namabidang:String,
        @Field("seksi") seksi:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxbidang/update/{id}")
    fun updateBidang(
        @Path("id") id:Int,
        @Field("namabidang") namabidang:String,
        @Field("seksi") seksi:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxbidang/delete/{id}")
    fun deleteBidang(
        @Path("id") id:Int,
    ): Call<SubmitModel>


    @GET("dtxkelurahan")
    fun getKelurahan(): Call<ResponseKelurahan>

    @FormUrlEncoded
    @POST("dtxkelurahan/store")
    fun insertKelurahan(
        @Field("namakelurahan") namakelurahan:String,
        @Field("namakecamatan") namakecamatan:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxkelurahan/update/{id}")
    fun updateKelurahan(
        @Path("id") id:Int,
        @Field("namakelurahan") namakelurahan:String,
        @Field("namakecamatan") namakecamatan:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxkelurahan/delete/{id}")
    fun deleteKelurahan(
        @Field("id") id:Int,
    ): Call<SubmitModel>

    @GET("dtxlokasi")
    fun getLokasi(): Call<ResponseLokasi>

    @GET("dtxpegawai")
    fun getPegawai(): Call<ResponsePegawai>

    @FormUrlEncoded
    @POST("dtxpegawai/store")
    fun insertPegawai(
        @Field("NIP") nip:String,
        @Field("namapegawai") namapegawai:String,
        @Field("jabatan") jabatan:String,
        @Field("bidang_id") bidang_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxpegawai/update/{NIP}")
    fun updatePegawai(
        @Path("NIP") nip:String,
        @Field("namapegawai") namapegawai:String,
        @Field("jabatan") jabatan:String,
        @Field("bidang_id") bidang_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxpegawai/delete/{id}")
    fun deletePegawai(
        @Path("NIP") nip:String,
    ): Call<SubmitModel>

    @GET("dtxpengaduan")
    fun getPengaduan(): Call<ResponsePengaduan>

    @GET("dtxperangkatdesa")
    fun getPerangkatDesa(): Call<ResponsePerangkatdesa>

    @FormUrlEncoded
    @POST("dtxperangkatdesa/store")
    fun insertPerangkatDesa(
        @Field("namapd") namapd:String,
        @Field("kelurahan_id") kelurahan_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxperangkatdesa/update/{id}")
    fun updatePerangkatDesa(
        @Path("id") id:Int,
        @Field("namapd") namapd:String,
        @Field("kelurahan_id") kelurahan_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxperangkatdesa/delete/{id}")
    fun deletePerangkatDesa(
        @Path("id") id:Int,
    ): Call<SubmitModel>
}