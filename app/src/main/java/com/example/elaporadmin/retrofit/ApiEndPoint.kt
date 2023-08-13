package com.example.elaporadmin.retrofit

import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.dao.Login
import com.example.elaporadmin.dao.ResponseBidang
import com.example.elaporadmin.dao.ResponseKecamatan
import com.example.elaporadmin.dao.ResponseKelurahan
import com.example.elaporadmin.dao.ResponseLokasi
import com.example.elaporadmin.dao.ResponsePegawai
import com.example.elaporadmin.dao.ResponsePengaduan
import com.example.elaporadmin.dao.ResponsePerangkatdesa
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiEndPoint {

    @GET("dtxbidang")
    suspend fun getBidang(): Response<ResponseBidang>

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


    @DELETE("dtxbidang/delete/{id}")
    fun deleteBidang(
        @Path("id") id:Int,
    ): Call<SubmitModel>


    @GET("dtxkecamatan")
    suspend fun getKecamatan(): Response<ResponseKecamatan>

    @FormUrlEncoded
    @POST("dtxkecamatan/store")
    suspend fun insertKecamatan(
        @Field("namakecamatan") namakecamatan:String
    ): Response<SubmitModel>

    @FormUrlEncoded
    @POST("dtxkecamatan/update/{id}")
    fun updateKecamatan(
        @Path("id") id:Int,
        @Field("namakecamatan") namakecamatan:String,
    ): Response<SubmitModel>

    @DELETE("dtxkecamatan/delete/{id}")
    suspend fun deleteKecamatan(
        @Path("id") id:Int,
    ): Response<SubmitModel>

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

    @DELETE("dtxkelurahan/delete/{id}")
    fun deleteKelurahan(
        @Path("id") id:Int,
    ): Call<SubmitModel>

    @GET("dtxlokasi")
    suspend fun getLokasi(): Response<ResponseLokasi>

    @GET("dtxlokasi/page/{id}")
    fun getLokasiPage(
        @Path("id") id:Int
    ): Call<ResponseLokasi>

    @FormUrlEncoded
    @POST("dtxlokasi/store")
    fun insertLokasi(
        @Field("datalokasi") datalokasi:String,
        @Field("latitude") latitude:Int,
        @Field("longitude") longitude:Int,
        @Field("foto") foto:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxlokasi/update/{id}")
    fun updateLokasi(
        @Path("id") id:Int,
        @Field("datalokasi") datalokasi:String,
        @Field("latitude") latitude:Int,
        @Field("longitude") longitude:Int,
        @Field("foto") foto:String,
    ): Call<SubmitModel>

    @DELETE("dtxlokasi/delete/{id}")
    fun deleteLokasi(
        @Path("id") id:Int,
    ): Call<SubmitModel>

    @GET("dtxpegawai")
    fun getPegawai(): Call<ResponsePegawai>

    @FormUrlEncoded
    @POST("dtxpegawai/store")
    fun insertPegawai(
        @Field("NIP") nip:String,
        @Field("namapegawai") namapegawai:String,
        @Field("jabatan") jabatan:String,
        @Field("bidang_id") bidang_id:Int,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxpegawai/update/{NIP}")
    fun updatePegawai(
        @Path("NIP") nip:String,
        @Field("namapegawai") namapegawai:String,
        @Field("jabatan") jabatan:String,
        @Field("bidang_id") bidang_id:Int,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<SubmitModel>

    @DELETE("dtxpegawai/delete/{NIP}")
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
        @Field("nik") nik:String,
        @Field("namapd") namapd:String,
        @Field("kelurahan_id") kelurahan_id:Int,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxperangkatdesa/update/{nik}")
    fun updatePerangkatDesa(
        @Path("nik") nik: String,
        @Field("namapd") namapd:String,
        @Field("kelurahan_id") kelurahan_id:Int,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<SubmitModel>


    @DELETE("dtxperangkatdesa/delete/{nik}")
    fun deletePerangkatDesa(
        @Path("nik") nik:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxuser/login")
    suspend fun login(
        @Field("email") email:String,
        @Field("password") password:String
    ):Response<Login>
}