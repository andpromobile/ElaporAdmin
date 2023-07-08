package com.example.elaporadmin.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    var gson = GsonBuilder()
        .setLenient()
        .create()

//    http://192.168.1.11/
//    http://192.168.1.4
    const val BASE_URL = "http://192.168.1.8/elapor/"
    val endPoint:ApiEndPoint

        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(ApiEndPoint::class.java)
        }

    private val client: OkHttpClient
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }
}