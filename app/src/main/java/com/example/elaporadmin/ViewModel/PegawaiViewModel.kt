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
    private val pesanLiveData = MutableLiveData<String>()

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

    fun insertPegawai(NIP:String, namapegawai:String, jabatan:String, bidang_id:Int){
        ApiService.endPoint.insertPegawai(
            NIP, namapegawai, jabatan, bidang_id
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

    fun updatePegawai(NIP:String, namapegawai:String, jabatan:String, bidang_id:Int){
        ApiService.endPoint.updatePegawai(
            NIP, namapegawai, jabatan, bidang_id
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

    fun deletePegawai(nip:String){

        ApiService.endPoint.deletePegawai(
            nip
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

    fun observePegawaiLiveData():LiveData<List<Pegawai>>{
        return pegawaiLiveData
    }

    fun observePesanLiveData():LiveData<String>{
        return pesanLiveData
    }

}