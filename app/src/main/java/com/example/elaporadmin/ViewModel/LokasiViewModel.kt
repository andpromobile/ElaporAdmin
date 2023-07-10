package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Lokasi
import com.example.elaporadmin.dao.ResponseLokasi
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LokasiViewModel: ViewModel() {
    private var lokasiLiveData = MutableLiveData<List<Lokasi>>()

    fun getLokasi(){
        ApiService.endPoint.getLokasi()
            .enqueue(object: Callback<ResponseLokasi> {
                override fun onResponse(
                    call: Call<ResponseLokasi>,
                    response: Response<ResponseLokasi>
                ) {
                    lokasiLiveData.value = response.body()!!.data
                }

                override fun onFailure(call: Call<ResponseLokasi>, t: Throwable) {
                    Log.d("TAG",t.message.toString())
                }

            })
    }

    fun observeLokasiLiveData():LiveData<List<Lokasi>>{
        return lokasiLiveData
    }
}