package com.example.elaporadmin.kelurahan

import com.google.gson.annotations.SerializedName

class ResponseKelurahan (
    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Kelurahan> = arrayListOf()
)