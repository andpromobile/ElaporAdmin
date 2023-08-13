package com.example.elaporadmin.dao

import java.sql.Date
import java.sql.Timestamp

class Pengaduan (
    var id:Int? = 0,
    var token: String? = null,
    var nama: String? = null,
    var telp: String? = null,
    var judulpengaduan: String? = null,
    var isipengaduan: String? = null,
    var tanggalpengaduan: Date? = null,
    var foto: String? = null,
    var status: String? = null,
    var lokasi_id:Int? = 0,
    var bidang_id:Int? = 0,
    var kelurahan_id:Int? = 0,
    var kecamatan_id:Int? = 0,
    var created_at: Timestamp? = null,
    var updated_at: Timestamp? = null
)