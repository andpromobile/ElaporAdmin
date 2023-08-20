package com.example.elaporadmin.perangkatdesa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerangkatDesaViewModel:ViewModel() {
    private var perangkatDesaLiveData = MutableLiveData<List<Perangkatdesa>>()
    private val pesanLiveData = MutableLiveData<String>()

    fun getPerangkatDesa(){
        ApiService.api.getPerangkatDesa()
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

    fun insertPerangkatDesa(nik:String, namapd:String, kelurahan_id:Int,email: String, password: String){
        ApiService.api.insertPerangkatDesa(
            nik, namapd, kelurahan_id, email, password
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

    fun updatePerangkatDesa(nik:String, namapd:String, kelurahan_id:Int,email: String, password: String){
        ApiService.api.updatePerangkatDesa(
            nik, namapd, kelurahan_id, email, password
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

    fun deletePerangkatDesa(nik: String){

        ApiService.api.deletePerangkatDesa(
            nik
        ).enqueue(object:Callback<SubmitModel>{
            override fun onResponse(
                call: Call<SubmitModel>,
                response: Response<SubmitModel>
            ) {
                if (response.isSuccessful){
                    pesanLiveData.value = response.body()!!.message
                }
            }

            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                pesanLiveData.value = t.toString()
            }

        })
    }

    fun observePerangkatDesaLiveData():LiveData<List<Perangkatdesa>>{
        return perangkatDesaLiveData
    }

    fun observePesanLiveData():LiveData<String>{
        return pesanLiveData
    }
}