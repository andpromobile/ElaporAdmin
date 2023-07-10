package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.dao.ResponseBidang
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BidangViewModel: ViewModel() {
    private var bidangLiveData = MutableLiveData<List<Bidang>>()
    private var pesanLiveData = MutableLiveData<String>()
    fun getBidang() {
        ApiService.endPoint.getBidang()
            .enqueue(object  : Callback<ResponseBidang> {
            override fun onResponse(
                call: Call<ResponseBidang>,
                response: Response<ResponseBidang>,
            ) {
                if (response.body()!=null){
                    bidangLiveData.value = response.body()!!.data

                    for (i in response.body()!!.data){
                        Log.d("TES", i.id.toString())
                        Log.d("TES", i.namabidang.toString())
                        Log.d("TES", i.seksi.toString())
                    }
                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<ResponseBidang>, t: Throwable) {
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