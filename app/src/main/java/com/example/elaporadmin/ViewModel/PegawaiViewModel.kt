package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Pegawai
import com.example.elaporadmin.dao.ResponsePegawai
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PegawaiViewModel:ViewModel() {
    private var pegawaiLiveData = MutableLiveData<List<Pegawai>>()

    fun getPegawai(){
        ApiService.endPoint.getPegawai()
            .enqueue(object: Callback<ResponsePegawai> {
                override fun onResponse(
                    call: Call<ResponsePegawai>,
                    response: Response<ResponsePegawai>
                ) {
                    pegawaiLiveData.value = response.body()!!.data
                }

                override fun onFailure(call: Call<ResponsePegawai>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }

            })
    }

    fun observePegawaiLiveData():LiveData<List<Pegawai>>{
        return pegawaiLiveData
    }

}