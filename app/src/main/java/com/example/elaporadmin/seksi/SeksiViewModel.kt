package com.example.elaporadmin.seksi

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

class SeksiViewModel: ViewModel() {
    private val seksiLiveData by lazy { MutableLiveData<List<Seksi>>() }
    private val pesanLiveData by lazy { MutableLiveData<String>() }

    fun getSeksi() {
       viewModelScope.launch{
            val response = ApiService.api.getSeksi()
            if (response.isSuccessful){
                    seksiLiveData.value = response.body()!!.data
            }
        }
    }

    fun insertSeksi(namaseksi:String, bidangid:Int){
        ApiService.api.insertSeksi(
            namaseksi, bidangid
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

    fun updateSeksi(id:Int, namaseksi:String, bidangid:Int){
        ApiService.api.updateSeksi(
            id, namaseksi, bidangid
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

    fun deleteSeksi(id:Int){

        ApiService.api.deleteSeksi(
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

    fun observeSeksiLiveData() : LiveData<List<Seksi>> {
        return seksiLiveData
    }

    fun observePesanLiveData():LiveData<String>{
        return pesanLiveData
    }
}