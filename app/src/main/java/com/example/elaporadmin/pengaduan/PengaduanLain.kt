package com.example.elaporadmin.pengaduan

import java.sql.Timestamp

class PengaduanLain (
    val id:Int,
    val token:String?,
    val nama:String?,
    val telp:String?,
    val namalokasi:String?,
    val alamat:String?,
    val judulpengaduan:String?,
    val isipengaduan:String?,
    val tanggalpengaduan:String?,
    val latitude:String?,
    val longitude:String?,
    val foto:String?,
    val status:String?,
    val kelurahan_id:Int,
    val namakelurahan:String
)
