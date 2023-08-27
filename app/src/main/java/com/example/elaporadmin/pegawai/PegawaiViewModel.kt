package com.example.elaporadmin.pegawai

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

class PegawaiViewModel:ViewModel() {
    private var pegawaiLiveData = MutableLiveData<List<Pegawai>>()
    private val pesanLiveData = MutableLiveData<String>()

    fun getPegawaiByNik(nik: String?){
        viewModelScope.launch {
            val response = ApiService.api.getPegawaiByNik(nik)
            if (response.isSuccessful){
                pegawaiLiveData.value = response.body()!!.data
            }
        }
    }

    fun updateProfilPegawai(nik: String, email: String, password: String){
        viewModelScope.launch {
            val response = ApiService.api.updateProfilPegawai(nik, email, password)
            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
    }

    fun getPegawai(){
        ApiService.api.getPegawai()
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

    fun insertPegawai(NIP:String, namapegawai:String, jabatan:String, bidang_id:Int, email:String, password:String){
        ApiService.api.insertPegawai(
            NIP, namapegawai, jabatan, bidang_id, email, password
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

    fun updatePegawai(NIP:String,
                      namapegawai:String,
                      jabatan:String,
                      bidang_id:Int,
                      email: String,
                      password: String){

        viewModelScope.launch{
            val response = ApiService.api.updatePegawai(
                NIP,
                namapegawai,
                jabatan,
                bidang_id,
                email,
                password
            )

            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
//        ApiService.api.updatePegawai(
//            NIP,
//            namapegawai,
//            jabatan,
//            bidang_id,
//            email,
//            password
//        )
//            .enqueue(object: Callback<SubmitModel>{
//                override fun onResponse(
//                    call: Call<SubmitModel>,
//                    response: Response<SubmitModel>
//                ) {
//                    if (response.isSuccessful){
//                        pesanLiveData.value = response.body()!!.message
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<SubmitModel>,
//                    t: Throwable
//                ) {
//                    pesanLiveData.value = t.toString()
//                }
//            })
    }

    fun deletePegawai(nip:String){

        ApiService.api.deletePegawai(
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