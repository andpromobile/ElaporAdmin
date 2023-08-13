package com.example.elaporadmin.retrofit

import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    companion object{
        private val lists: MutableList<ConnectionSpec> = mutableListOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)

        private var gson = GsonBuilder()
            .setLenient()
            .create()

        private const val BASE_URL = "https://pupr.hstkab.go.id/elapor/api/"

        private val retrofit by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .connectionSpecs(lists)
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(ApiEndPoint::class.java)
        }
    }

//
//
//
//    val endPoint:ApiEndPoint
//
//        get() {
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//
//            return retrofit.create(ApiEndPoint::class.java)
//        }
//
//    private val client: OkHttpClient
//        get() {
//
//            val lists = Arrays.asList(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)
//
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//            return OkHttpClient.Builder()
//                .connectionSpecs(lists)
//                .addInterceptor(interceptor)
//                .build()
//        }
}