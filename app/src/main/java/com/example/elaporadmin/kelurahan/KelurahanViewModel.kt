package com.example.elaporadmin.kelurahan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.ViewModel.SubmitModel
import com.example.elaporadmin.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelurahanViewModel:ViewModel() {
    private var kelurahanLiveData = MutableLiveData<List<Kelurahan>>()
    private val pesanLiveData = MutableLiveData<String>()

    fun getKelurahan() {
        ApiService.api.getKelurahan()
            .enqueue(object  : Callback<ResponseKelurahan> {
                override fun onResponse(
                    call: Call<ResponseKelurahan>,
                    response: Response<ResponseKelurahan>,
                ) {
                    if (response.body()!=null){
                        kelurahanLiveData.value = response.body()!!.data
                    }
                    else{
                        return
                    }
                }
                override fun onFailure(call: Call<ResponseKelurahan>, t: Throwable) {
                    Log.d("TAG",t.message.toString())
                }
            })
    }

    fun insertKelurahan(namakelurahan:String, namakecamatan:String){
        ApiService.api.insertKelurahan(
            namakelurahan, namakecamatan
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

    fun updateKelurahan(id:Int, namakelurahan:String, namakecamatan: String){
        ApiService.api.updateKelurahan(
            id, namakelurahan, namakecamatan
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

    fun deleteKelurahan(id:Int){

        ApiService.api.deleteKelurahan(
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

    fun observeKelurahanLiveData() : LiveData<List<Kelurahan>> {
        return kelurahanLiveData
    }

    fun observePesanLiveData():LiveData<String>{
        return pesanLiveData
    }
}