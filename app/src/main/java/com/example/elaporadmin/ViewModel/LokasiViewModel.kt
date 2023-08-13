package com.example.elaporadmin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Lokasi
import com.example.elaporadmin.dao.ResponseLokasi
import com.example.elaporadmin.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LokasiViewModel: ViewModel() {
    private var lokasiLiveData = MutableLiveData<List<Lokasi>>()
    private val pesanLiveData = MutableLiveData<String>()

    fun getLokasi(){
        GlobalScope.launch (Dispatchers.IO){
            val response = ApiService.api.getLokasi()
            if (response.isSuccessful){
                withContext(Dispatchers.Main){
                    lokasiLiveData.value = response.body()!!.data
                }
            }
        }
//        ApiService.api.getLokasi()
//            .enqueue(object: Callback<ResponseLokasi> {
//                override fun onResponse(
//                    call: Call<ResponseLokasi>,
//                    response: Response<ResponseLokasi>
//                ) {
//
//                    if (response.body()!=null){
//                        lokasiLiveData.value = response.body()!!.data
//
//                        Log.d("HASIL LOKASI",lokasiLiveData.value.toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseLokasi>, t: Throwable) {
//                    Log.d("TAG ON Failure",t.message.toString())
//                }
//
//            })
    }

    fun insertLokasi(
        datalokasi:String,
        latitude:Int,
        longitude:Int,
        foto: String    ){
        ApiService.api.insertLokasi(
            datalokasi,
            latitude,
            longitude,
            foto
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

    fun updateLokasi(id:Int,
                      datalokasi:String,
                      latitude:Int,
                      longitude:Int,
                      foto: String){

        ApiService.api.updateLokasi(
            id,
            datalokasi,
            latitude,
            longitude,
            foto,
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

    fun deleteLokasi(id:Int){

        ApiService.api.deleteLokasi(
            id
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
    fun observeLokasiLiveData():LiveData<List<Lokasi>>{
        return lokasiLiveData
    }

    fun observePesanLiveData():LiveData<String>{
        return pesanLiveData
    }
}