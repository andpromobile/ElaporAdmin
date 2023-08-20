package com.example.elaporadmin.pegawai

import java.sql.Timestamp

class Pegawai (
    var NIP:String = "",
    var namapegawai: String? = null,
    var jabatan: String? = null,
    var bidang_id:Int? = 0,
    var user_id:Int? = 0,
    var email:String? = null,
    var created_at: Timestamp? = null,
    var updated_at: Timestamp? = null,
)