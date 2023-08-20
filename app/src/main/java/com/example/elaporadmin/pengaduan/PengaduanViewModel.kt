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

    fun observePengaduanLiveData():LiveData<List<Pengaduan>>{
        return pengaduanLiveData
    }

}