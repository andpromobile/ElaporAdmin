package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Pegawai
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PegawaiViewModel:ViewModel() {
    private var pegawaiLiveData = MutableLiveData<List<Pegawai>>()

    fun getPegawai(){
        ApiService.endPoint.getPegawai()
            .enqueue(object: Callback<List<Pegawai>> {
                override fun onResponse(
                    call: Call<List<Pegawai>>,
                    response: Response<List<Pegawai>>
                ) {
                    pegawaiLiveData.value = response.body()!!
                }

                override fun onFailure(call: Call<List<Pegawai>>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }

            })
    }

    fun observePegawaiLiveData():LiveData<List<Pegawai>>{
        return pegawaiLiveData
    }

}