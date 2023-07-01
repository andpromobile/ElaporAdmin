package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.dao.Kelurahan
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelurahanViewModel:ViewModel() {
    private var kelurahanLiveData = MutableLiveData<List<Kelurahan>>()

    fun getKelurahan() {
        ApiService.endPoint.getKelurahan()
            .enqueue(object  : Callback<List<Kelurahan>> {
                override fun onResponse(
                    call: Call<List<Kelurahan>>,
                    response: Response<List<Kelurahan>>,
                ) {
                    if (response.body()!=null){
                        kelurahanLiveData.value = response.body()!!
                    }
                    else{
                        return
                    }
                }
                override fun onFailure(call: Call<List<Kelurahan>>, t: Throwable) {
                    Log.d("TAG",t.message.toString())
                }
            })
    }

    fun observeKelurahanLiveData() : LiveData<List<Kelurahan>> {
        return kelurahanLiveData
    }
}