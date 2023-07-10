package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Pengaduan
import com.example.elaporadmin.dao.ResponsePengaduan
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengaduanViewModel:ViewModel() {
    private var pengaduanLiveData = MutableLiveData<List<Pengaduan>>()

    fun getPengaduan(){
        ApiService.endPoint.getPengaduan()
            .enqueue(object :Callback<ResponsePengaduan>{
                override fun onResponse(
                    call: Call<ResponsePengaduan>,
                    response: Response<ResponsePengaduan>
                ) {
                    pengaduanLiveData.value = response.body()!!.data
                }

                override fun onFailure(call: Call<ResponsePengaduan>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }

            })
    }

    fun observePengaduanLiveData():LiveData<List<Pengaduan>>{
        return pengaduanLiveData
    }

}