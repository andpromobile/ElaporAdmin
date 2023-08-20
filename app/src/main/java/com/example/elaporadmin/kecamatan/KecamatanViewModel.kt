package com.example.elaporadmin.kecamatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaporadmin.kecamatan.Kecamatan
import com.example.elaporadmin.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KecamatanViewModel: ViewModel() {
    private var kecamatanLiveData = MutableLiveData<List<Kecamatan>>()
    private val pesanLiveData = MutableLiveData<String>()

    fun getKecamatan() {
        GlobalScope.launch(Dispatchers.IO){
            val response = ApiService.api.getKecamatan()
            if (response.isSuccessful){
                withContext(Dispatchers.Main){
                    kecamatanLiveData.value = response.body()!!.data
                }
            }
        }
        
    }

    fun insertKecamatan(namaKecamatan:String){

        viewModelScope.launch{
            val response = ApiService.api.insertKecamatan(namaKecamatan)
            if (response.isSuccessful){
                    pesanLiveData.value = response.body()!!.message
            }
        }
    }

    fun updateKecamatan(id:Int, namaKecamatan:String){
        viewModelScope.launch(){
            val response = ApiService.api.updateKecamatan(id, namaKecamatan)
            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
    }
    
    fun deleteKecamatan(id:Int){
        viewModelScope.launch{
            val response = ApiService.api.deleteKecamatan(id)
            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
    }

    fun observeKecamatanLiveData() : LiveData<List<Kecamatan>> {
        return kecamatanLiveData
    }

    fun observePesanLiveData(): LiveData<String> {
        return pesanLiveData
    }
}