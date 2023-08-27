package com.example.elaporadmin.perangkatdesa

import java.sql.Timestamp

class Perangkatdesa (
    var nik:String = "",
    var namapd: String? = null,
    var kelurahan_id:Int? = 0,
    var namakecamatan:String? = null,
    var level:String? = null,
    var email:String? = null,
    var password:String? = null,
    var created_at: Timestamp? = null,
    var updated_at: Timestamp? = null,
)