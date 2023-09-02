package com.example.elaporadmin.seksi

import com.example.elaporadmin.bidang.Bidang
import com.google.gson.annotations.SerializedName

class ResponseSeksi (
//    @SerializedName("status") var status  : Boolean?        = null,
//    @SerializedName("message") var message : String?         = null,
    @SerializedName("data") var data    : ArrayList<Seksi> = arrayListOf()
)