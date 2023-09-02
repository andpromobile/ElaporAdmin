package com.example.elaporadmin.pengaduan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaporadmin.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengaduanViewModel:ViewModel() {
    private var pengaduanLiveData = MutableLiveData<List<Pengaduan>>()
    private val pesanLiveData = MutableLiveData<String>()

    fun getPengaduan(){

        viewModelScope.launch{
            val response = ApiService.api.getPengaduan()
            if (response.isSuccessful){
                pengaduanLiveData.value = response.body()!!.data
            }
        }

//        ApiService.api.getPengaduan()
//            .enqueue(object :Callback<ResponsePengaduan>{
//                override fun onResponse(
//                    call: Call<ResponsePengaduan>,
//                    response: Response<ResponsePengaduan>
//                ) {
//                    pengaduanLiveData.value = response.body()!!.data
//                }
//
//                override fun onFailure(call: Call<ResponsePengaduan>, t: Throwable) {
//                    Log.d("TAG", t.message.toString())
//                }
//
//            })
    }

    fun getPengaduanByKecamatanId(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.getPengaduanByKecamatanId(id)
            if (response.isSuccessful){
                pengaduanLiveData.value = response.body()!!.data
            }

        }
    }

    fun pengaduanDeny(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.pengaduanDeny(id)
            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
    }

    fun pengaduanVerifikasi(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.pengaduanVerifikasi(id)
            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
    }

    fun pengaduanVerifikasiPd(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.pengaduanVerifikasiPd(id)
            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
    }

    fun pengaduanVerifikasi1(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.pengaduanVerifikasi1(id)
            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
    }

    fun getPengaduanByBidangId(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.getPengaduanByBidangId(id)
            if (response.isSuccessful){
                pengaduanLiveData.value = response.body()!!.data
            }

        }
    }

    fun observePengaduanLiveData():LiveData<List<Pengaduan>>{
        return pengaduanLiveData
    }

    fun observePesanLiveData(): LiveData<String> {
        return pesanLiveData
    }

}