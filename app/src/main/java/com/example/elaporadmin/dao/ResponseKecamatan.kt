package com.example.elaporadmin.dao

import com.google.gson.annotations.SerializedName

class ResponseKecamatan {
    @SerializedName("data") var data : ArrayList<Kecamatan> = arrayListOf()
}