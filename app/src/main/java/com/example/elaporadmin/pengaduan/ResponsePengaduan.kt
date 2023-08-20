package com.example.elaporadmin.pengaduan

import com.google.gson.annotations.SerializedName

class ResponsePengaduan (
    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Pengaduan> = arrayListOf()
)