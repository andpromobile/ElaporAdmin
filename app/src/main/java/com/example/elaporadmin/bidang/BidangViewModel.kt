package com.example.elaporadmin.bidang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BidangViewModel: ViewModel() {
    private val bidangLiveData by lazy { MutableLiveData<List<Bidang>>() }
    private val pesanLiveData by lazy { MutableLiveData<String>() }

    fun getBidang() {
       viewModelScope.launch{
            val response = ApiService.api.getBidang()
            if (response.isSuccessful){
                    bidangLiveData.value = response.body()!!.data
            }
        }
    }

    fun insertBidang(namabidang:String, seksi:String){
        ApiService.api.insertBidang(
            namabidang, seksi
        )
            .enqueue(object: Callback<SubmitModel>{
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                   if (response.isSuccessful){
                        pesanLiveData.value = response.body()!!.message
                    }
                }

                override fun onFailure(
                    call: Call<SubmitModel>,
                    t: Throwable
                ) {
                    pesanLiveData.value = t.toString()
                }
            })
    }

    fun updateBidang(id:Int, namabidang:String, seksi: String){
        ApiService.api.updateBidang(
            id, namabidang, seksi
        )
            .enqueue(object: Callback<SubmitModel>{
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful){
                        pesanLiveData.value = response.body()!!.message
                    }
                }

                override fun onFailure(
                    call: Call<SubmitModel>,
                    t: Throwable
                ) {
                    pesanLiveData.value = t.toString()
                }
            })
    }

    fun deleteBidang(id:Int){

        ApiService.api.deleteBidang(
            id
        ).enqueue(object:Callback<SubmitModel>{
            override fun onResponse(
                call: Call<SubmitModel>,
                response: Response<SubmitModel>
            ) {
                Log.d("ERROR", "ERROR JER")
                if (response.isSuccessful){
                    pesanLiveData.value = response.body()!!.message
                }
            }

            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                pesanLiveData.value = t.toString()

                Log.d("error", t.toString())
            }

        })
    }

    fun observeBidangLiveData() : LiveData<List<Bidang>> {
        return bidangLiveData
    }

    fun observePesanLiveData():LiveData<String>{
        return pesanLiveData
    }
}