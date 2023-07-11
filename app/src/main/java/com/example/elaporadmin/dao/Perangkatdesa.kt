package com.example.elaporadmin.dao

import java.sql.Timestamp

class Perangkatdesa (
    var id:Int = 0,
    var namapd: String? = null,
    var kelurahan_id:Int? = 0,
    var created_at: Timestamp? = null,
    var updated_at: Timestamp? = null,
)