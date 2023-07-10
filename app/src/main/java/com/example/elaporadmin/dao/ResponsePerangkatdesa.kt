package com.example.elaporadmin.dao

import com.google.gson.annotations.SerializedName

class ResponsePerangkatdesa (
    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Perangkatdesa> = arrayListOf()
)