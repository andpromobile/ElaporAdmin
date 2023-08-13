package com.example.elaporadmin.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elaporadmin.dao.Kecamatan
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

        GlobalScope.launch(Dispatchers.IO){
            val response = ApiService.api.insertKecamatan(namaKecamatan)
            if (response.isSuccessful){
                withContext(Dispatchers.Main){
                    pesanLiveData.value = response.body()!!.message
                }
            }
        }
    }

    fun updateKecamatan(id:Int, namaKecamatan:String){
        GlobalScope.launch(Dispatchers.IO){
            val response = ApiService.api.updateKecamatan(id, namaKecamatan)
            if (response.isSuccessful){
                pesanLiveData.value = response.body()!!.message
            }
        }
    }
    
    fun deleteKecamatan(id:Int){
        GlobalScope.launch(Dispatchers.IO){
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