package com.example.elaporadmin.pegawai

import com.google.gson.annotations.SerializedName

class ResponsePegawai (
    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Pegawai> = arrayListOf()
)