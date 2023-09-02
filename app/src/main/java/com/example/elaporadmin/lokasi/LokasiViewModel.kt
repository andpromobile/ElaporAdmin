package com.example.elaporadmin.lokasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.math.ceil

class LokasiViewModel: ViewModel() {
    private val lokasiLiveData by lazy { MutableLiveData<List<Lokasi>>()}
    private val pesanLiveData by lazy { MutableLiveData<String>() }

    var page = 1

    fun getLokasi(){
        viewModelScope.launch{
            val response = ApiService.api.getLokasi()
            if (response.isSuccessful){
                    lokasiLiveData.value = response.body()!!.data
            }
        }
    }

    fun cari(keyword:String){
        viewModelScope.launch{
            val response = ApiService.api.cari(keyword)
            if (response.isSuccessful){
                lokasiLiveData.value = response.body()!!.data
            }
        }
    }

    fun getLokasiByPage(){
        viewModelScope.launch {
            try {
                val response = ApiService.api.getLokasiPage(page)
                if (response.isSuccessful){
                    lokasiLiveData.value = response.body()!!.data
                }
//                val response = repository.page( category.value, query, page )
//                news.value = response
//                val totalResults: Double = response.totalResults / 20.0
//                total = ceil(totalResults).toInt()
//                page ++
//                loading.value = false
//                loadMore.value = false
            } catch (e: Exception) {
                pesanLiveData.value = "Terjadi kesalahan" // e.localizedMessage
            }

        }
    }

    fun insertLokasi(
        datalokasi:String,
        latitude:Int,
        longitude:Int,
        foto: String    ){
        ApiService.api.insertLokasi(
            datalokasi,
            latitude,
            longitude,
            foto
        )
            .enqueue(object: Callback<SubmitModel>{
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful){
                        pesanLiveData.value = response.body()!!.message
                    }
                }

                override fun onFailure(
                    call: Call<SubmitModel>,
                    t: Throwable
                ) {
                    pesanLiveData.value = t.toString()
                }
            })
    }

    fun updateLokasi(id:Int,
                      datalokasi:String,
                      latitude:Int,
                      longitude:Int,
                      foto: String){

        ApiService.api.updateLokasi(
            id,
            datalokasi,
            latitude,
            longitude,
            foto,
        )
            .enqueue(object: Callback<SubmitModel>{
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful){
                        pesanLiveData.value = response.body()!!.message
                    }
                }

                override fun onFailure(
                    call: Call<SubmitModel>,
                    t: Throwable
                ) {
                    pesanLiveData.value = t.toString()
                }
            })
    }

    fun deleteLokasi(id:Int){

        ApiService.api.deleteLokasi(
            id
        ).enqueue(object:Callback<SubmitModel>{
            override fun onResponse(
                call: Call<SubmitModel>,
                response: Response<SubmitModel>
            ) {
                if (response.isSuccessful){
                    pesanLiveData.value = response.body()!!.message
                }
            }

            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                pesanLiveData.value = t.toString()
            }

        })
    }
    fun observeLokasiLiveData():LiveData<List<Lokasi>>{
        return lokasiLiveData
    }

    fun observePesanLiveData():LiveData<String>{
        return pesanLiveData
    }
}