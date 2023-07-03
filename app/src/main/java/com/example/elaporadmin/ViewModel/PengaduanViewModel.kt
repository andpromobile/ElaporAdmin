package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Pengaduan
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengaduanViewModel:ViewModel() {
    private var pengaduanLiveData = MutableLiveData<List<Pengaduan>>()

    fun getPengaduan(){
        ApiService.endPoint.getPengaduan()
            .enqueue(object :Callback<List<Pengaduan>>{
                override fun onResponse(
                    call: Call<List<Pengaduan>>,
                    response: Response<List<Pengaduan>>
                ) {
                    pengaduanLiveData.value = response.body()!!
                }

                override fun onFailure(call: Call<List<Pengaduan>>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }

            })
    }

    fun observePengaduanLiveData():LiveData<List<Pengaduan>>{
        return pengaduanLiveData
    }

}