package com.example.elaporadmin.pengaduan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaporadmin.retrofit.ApiService
import kotlinx.coroutines.launch

class PengaduanLainViewModel:ViewModel() {
    private var pengaduanLiveData = MutableLiveData<List<PengaduanLain>>()

    fun getPengaduanLain(){

        viewModelScope.launch{
            val response = ApiService.api.getPengaduanLain()
            if (response.isSuccessful){
                pengaduanLiveData.value = response.body()!!.data
            }
        }
    }
    fun getPengaduanLainByKelurahanId(id:Int){

        viewModelScope.launch{
            val response = ApiService.api.getPengaduanLainByKelurahanId(id)
            if (response.isSuccessful){
                pengaduanLiveData.value = response.body()!!.data
            }
        }

//        ApiService.api.getPengaduan()
//            .enqueue(object :Callback<ResponsePengaduan>{
//                override fun onResponse(
//                    call: Call<ResponsePengaduan>,
//                    response: Response<ResponsePengaduan>
//                ) {
//                    pengaduanLiveData.value = response.body()!!.data
//                }
//
//                override fun onFailure(call: Call<ResponsePengaduan>, t: Throwable) {
//                    Log.d("TAG", t.message.toString())
//                }
//
//            })
    }

    fun pengaduanLainDeny(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.pengaduanLainDeny(id)
        }
    }

    fun pengaduanVerifikasi(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.pengaduanVerifikasi(id)
        }
    }

    fun pengaduanVerifikasi1(id:Int){
        viewModelScope.launch {
            val response = ApiService.api.pengaduanVerifikasi1(id)
        }
    }

    fun observePengaduanLiveData():LiveData<List<PengaduanLain>>{
        return pengaduanLiveData
    }

}