package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Perangkatdesa
import com.example.elaporadmin.dao.ResponsePerangkatdesa
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerangkatDesaViewModel:ViewModel() {
    private var perangkatDesaLiveData = MutableLiveData<List<Perangkatdesa>>()

    fun getPerangkatDesa(){
        ApiService.endPoint.getPerangkatDesa()
            .enqueue(object:Callback<ResponsePerangkatdesa>{
                override fun onResponse(
                    call: Call<ResponsePerangkatdesa>,
                    response: Response<ResponsePerangkatdesa>
                ) {
                    perangkatDesaLiveData.value = response.body()!!.data
                }

                override fun onFailure(call: Call<ResponsePerangkatdesa>, t: Throwable) {
                   Log.d("TAG", t.message.toString())
                }

            })
    }

    fun observePerangkatDesaLiveData():LiveData<List<Perangkatdesa>>{
        return perangkatDesaLiveData
    }
}