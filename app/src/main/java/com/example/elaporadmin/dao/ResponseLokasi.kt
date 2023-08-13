package com.example.elaporadmin.dao

import com.google.gson.annotations.SerializedName

class ResponseLokasi (
    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("page") var page:Int?,
    @SerializedName("per_page") var per_page:Int?,
    @SerializedName("total") var total:Int?,
    @SerializedName("total_pages") var total_pages:Int?,
    @SerializedName("data"    ) var data    : ArrayList<Lokasi> = arrayListOf()
)