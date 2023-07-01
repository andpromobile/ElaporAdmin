package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BidangViewModel: ViewModel() {
    private var bidangLiveData = MutableLiveData<List<Bidang>>()

    fun getBidang() {
        ApiService.endPoint.getBidang()
            .enqueue(object  : Callback<List<Bidang>> {
            override fun onResponse(
                call: Call<List<Bidang>>,
                response: Response<List<Bidang>>,
            ) {
                if (response.body()!=null){
                    bidangLiveData.value = response.body()!!
                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<List<Bidang>>, t: Throwable) {
                Log.d("TAG",t.message.toString())
            }
        })
    }

    fun observeBidangLiveData() : LiveData<List<Bidang>> {
        return bidangLiveData
    }
}