package com.example.elaporadmin.kecamatan

import com.google.gson.annotations.SerializedName

class ResponseKecamatan {
    @SerializedName("data") var data : ArrayList<Kecamatan> = arrayListOf()
}