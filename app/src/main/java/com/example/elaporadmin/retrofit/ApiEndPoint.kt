package com.example.elaporadmin.retrofit

import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.dao.Login
import com.example.elaporadmin.bidang.ResponseBidang
import com.example.elaporadmin.kecamatan.ResponseKecamatan
import com.example.elaporadmin.kelurahan.ResponseKelurahan
import com.example.elaporadmin.lokasi.ResponseLokasi
import com.example.elaporadmin.pegawai.ResponsePegawai
import com.example.elaporadmin.pengaduan.ResponsePengaduan
import com.example.elaporadmin.pengaduan.ResponsePengaduanLain
import com.example.elaporadmin.perangkatdesa.ResponsePerangkatdesa
import com.example.elaporadmin.seksi.ResponseSeksi
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiEndPoint {

    @GET("dtxseksi")
    suspend fun getSeksi(): Response<ResponseSeksi>

    @FormUrlEncoded
    @POST("dtxseksi/store")
    fun insertSeksi(
        @Field("namaseksi") namaseksi:String,
        @Field("bidang_id") bidang_id:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxseksi/update/{id}")
    fun updateSeksi(
        @Path("id") id:Int,
        @Field("namaseksi") namaseksi:String,
        @Field("bidang_id") bidang_id:Int,
    ): Call<SubmitModel>


    @DELETE("dtxseksi/delete/{id}")
    fun deleteSeksi(
        @Path("id") id:Int,
    ): Call<SubmitModel>

    @GET("dtxbidang")
    suspend fun getBidang(): Response<ResponseBidang>

    @FormUrlEncoded
    @POST("dtxbidang/store")
    fun insertBidang(
        @Field("namabidang") namabidang:String,
//        @Field("seksi") seksi:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxbidang/update/{id}")
    fun updateBidang(
        @Path("id") id:Int,
        @Field("namabidang") namabidang:String,
//        @Field("seksi") seksi:String,
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
    suspend fun updateKecamatan(
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
        @Field("kecamatan_id") kecamatanid:Int,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxkelurahan/update/{id}")
    fun updateKelurahan(
        @Path("id") id:Int,
        @Field("namakelurahan") namakelurahan:String,
        @Field("kecamatan_id") kecamatanid:Int,
    ): Call<SubmitModel>

    @DELETE("dtxkelurahan/delete/{id}")
    fun deleteKelurahan(
        @Path("id") id:Int,
    ): Call<SubmitModel>

    @GET("dtxlokasi")
    suspend fun getLokasi(): Response<ResponseLokasi>

    @GET("dtxlokasi/page/{id}")
    suspend fun getLokasiPage(
        @Path("id") id:Int
    ): Response<ResponseLokasi>

    @GET("dtxlokasi/cari/{keyword}")
    suspend fun cari(
        @Path("keyword") keyword:String
    ): Response<ResponseLokasi>

    @GET("dtxlokasi/show/{id}")
    suspend fun getLokasiById(
        @Path("id") id:Int
    ): Response<ResponseLokasi>


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

    @GET("dtxpegawai/show/{nik}")
    suspend fun getPegawaiByNik(
        @Path("nik") nik: String?,
    ): Response<ResponsePegawai>

    @FormUrlEncoded
    @POST("dtxpegawai/updateProfile/{nik}")
    suspend fun updateProfilPegawai(
        @Path("nik") nik: String,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Response<SubmitModel>

    @FormUrlEncoded
    @POST("dtxpegawai/store")
    fun insertPegawai(
        @Field("NIP") nip:String,
        @Field("namapegawai") namapegawai:String,
        @Field("jabatan") jabatan:String,
        @Field("bidang_id") bidangid:Int,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxpegawai/update/{NIP}")
    suspend fun updatePegawai(
        @Path("NIP") nip:String,
        @Field("namapegawai") namapegawai:String,
        @Field("jabatan") jabatan:String,
        @Field("bidang_id") bidangid:Int,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Response<SubmitModel>

    @DELETE("dtxpegawai/delete/{NIP}")
    fun deletePegawai(
        @Path("NIP") nip:String,
    ): Call<SubmitModel>

    @GET("dtxpengaduan")
    suspend fun getPengaduan(): Response<ResponsePengaduan>

    @GET("dtxpengaduanlain")
    suspend fun getPengaduanLain(): Response<ResponsePengaduanLain>

    @GET("dtxpengaduanlain/verifikasi/{id}")
    suspend fun verifikasi(
        @Path("id") id:Int
    ): Response<SubmitModel>

    @GET("dtxpengaduan/bidang/{id}")
    suspend fun getPengaduanByBidangId(
        @Path("id") id:Int
    ): Response<ResponsePengaduan>

    @GET("dtxpengaduanlain/kelurahan/{id}")
    suspend fun getPengaduanLainByKelurahanId(
        @Path("id") id:Int
    ): Response<ResponsePengaduanLain>

    @GET("dtxpengaduan/deny/{id}")
    suspend fun pengaduanDeny(
        @Path("id") id:Int
    ): Response<SubmitModel>

    @GET("dtxpengaduanlain/deny/{id}")
    suspend fun pengaduanLainDeny(
        @Path("id") id:Int
    ): Response<SubmitModel>

    @GET("dtxpengaduan/verifikasi/{id}")
    suspend fun pengaduanVerifikasi(
        @Path("id") id:Int
    ): Response<SubmitModel>

    @GET("dtxpengaduan/verifikasi1/{id}")
    suspend fun pengaduanVerifikasi1(
        @Path("id") id:Int
    ): Response<SubmitModel>

    @GET("dtxpengaduan/verifikasiPd/{id}")
    suspend fun pengaduanVerifikasiPd(
        @Path("id") id:Int
    ): Response<SubmitModel>

    @GET("dtxpengaduan/kecamatan/{id}")
    suspend fun getPengaduanByKecamatanId(
        @Path("id") id: Int
    ):Response<ResponsePengaduan>

    @GET("dtxperangkatdesa")
    fun getPerangkatDesa(): Call<ResponsePerangkatdesa>

    @GET("dtxperangkatdesa/show/{nik}")
    suspend fun getPerangkatDesaByNik(
        @Path("nik") nik: String?,
    ): Response<ResponsePerangkatdesa>


    @FormUrlEncoded
    @POST("dtxperangkatdesa/store")
    fun insertPerangkatDesa(
        @Field("nik") nik:String,
        @Field("namapd") namapd:String,
        @Field("kecamatan_id") kelurahanid:Int,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxperangkatdesa/update/{nik}")
    fun updatePerangkatDesa(
        @Path("nik") nik: String,
        @Field("namapd") namapd:String,
        @Field("kelurahan_id") kelurahanid:Int,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("dtxperangkatdesa/updateProfile/{nik}")
    suspend fun updateProfilPerangkatDesa(
        @Path("nik") nik: String,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Response<SubmitModel>


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