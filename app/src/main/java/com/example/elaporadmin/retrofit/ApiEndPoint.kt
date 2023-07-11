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

    @FormUrlEncoded
    @POST("kelurahan-insert.php")
    fun insertKelurahan(
        @Field("namakelurahan") namakelurahan:String,
        @Field("namakecamatan") namakecamatan:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("kelurahan-update.php")
    fun updateKelurahan(
        @Field("id") id:Int,
        @Field("namakelurahan") namakelurahan:String,
        @Field("namakecamatan") namakecamatan:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("kelurahan-delete.php")
    fun deleteKelurahan(
        @Field("id") id:Int,
    ): Call<SubmitModel>

    @GET("lokasi.php")
    fun getLokasi(): Call<ResponseLokasi>

    @GET("pegawai.php")
    fun getPegawai(): Call<ResponsePegawai>

    @FormUrlEncoded
    @POST("pegawai-insert.php")
    fun insertPegawai(
        @Field("NIP") nip:String,
        @Field("namapegawai") namapegawai:String,
        @Field("jabatan") jabatan:String,
        @Field("bidang_id") bidang_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("pegawai-update.php")
    fun updatePegawai(
        @Field("NIP") nip:String,
        @Field("namapegawai") namapegawai:String,
        @Field("jabatan") jabatan:String,
        @Field("bidang_id") bidang_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("pegawai-delete.php")
    fun deletePegawai(
        @Field("NIP") nip:String,
    ): Call<SubmitModel>

    @GET("pengaduan.php")
    fun getPengaduan(): Call<ResponsePengaduan>

    @GET("perangkatdesa.php")
    fun getPerangkatDesa(): Call<ResponsePerangkatdesa>

    @FormUrlEncoded
    @POST("perangkatdesa-insert.php")
    fun insertPerangkatDesa(
        @Field("namapd") namapd:String,
        @Field("kelurahan_id") kelurahan_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("perangkatdesa-update.php")
    fun updatePerangkatDesa(
        @Field("id") id:Int,
        @Field("namapd") namapd:String,
        @Field("kelurahan_id") kelurahan_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("perangkatdesa-delete.php")
    fun deletePerangkatDesa(
        @Field("id") id:Int,
    ): Call<SubmitModel>
}