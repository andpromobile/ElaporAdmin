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
    private var pesanLiveData = MutableLiveData<String>()
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

    fun insertBidang(namabidang:String, seksi:String){
        ApiService.endPoint.insertBidang(
            namabidang, seksi
        )
            .enqueue(object: Callback<SubmitModel>{
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful){
                        pesanLiveData.value = response.body()!!.pesan

//                        Log.d("HASIL LIVE DATA",pesanLiveData.value.toString())

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
        ApiService.endPoint.updateBidang(
            id, namabidang, seksi
        )
            .enqueue(object: Callback<SubmitModel>{
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful){
                        pesanLiveData.value = response.body()!!.pesan

//                        Log.d("HASIL LIVE DATA",pesanLiveData.value.toString())

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

        ApiService.endPoint.deleteBidang(
            id
        ).enqueue(object:Callback<SubmitModel>{
            override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
                if (response.isSuccessful){
                    pesanLiveData.value = response.body()!!.pesan

                }
            }

            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                pesanLiveData.value = t.toString()
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