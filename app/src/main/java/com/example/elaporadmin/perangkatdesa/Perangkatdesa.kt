package com.example.elaporadmin.perangkatdesa

import java.sql.Timestamp

class Perangkatdesa (
    var nik:String = "",
    var namapd: String? = null,
    var kelurahan_id:Int? = 0,
    var created_at: Timestamp? = null,
    var updated_at: Timestamp? = null,
)