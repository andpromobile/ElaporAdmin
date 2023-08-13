package com.example.elaporadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Pengaduan

class ListPengaduanAdapter(
    val listPengaduan:ArrayList<Pengaduan>,
    val listener:OnAdapterListener):
RecyclerView.Adapter<ListPengaduanAdapter.ListPengaduanHolder>(){
    inner class ListPengaduanHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var btnVerifikasi:ImageButton = itemView.findViewById(R.id.btnVerifikasiPengaduanPegawai)
        var btnDetail:ImageButton = itemView.findViewById(R.id.detailPengaduanPegawai)
        var lokasiPengaduan:TextView = itemView.findViewById(R.id.lokasiPengaduanPegawai)
        var imageView:ImageButton = itemView.findViewById(R.id.ivPengaduanPegawai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPengaduanHolder {
        val view:View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_pengaduan1,
                parent,
                false
            )

        return ListPengaduanHolder(view)
    }

    override fun getItemCount(): Int = listPengaduan.size

    override fun onBindViewHolder(
        holder: ListPengaduanHolder,
        position: Int
    ) {
        val pengaduan = listPengaduan[position]

        holder.lokasiPengaduan.text = pengaduan.judulpengaduan

        Glide
            .with(holder.itemView.context)
            .load("https://goo.gl/gEgYUd")
            .into(holder.imageView)

        holder.btnDetail.setOnClickListener {
            listener.onDetail(pengaduan)
        }

        holder.btnVerifikasi.setOnClickListener {
            listener.onVerifikasi(pengaduan)
        }
    }

    interface OnAdapterListener{
        fun onClick(pengaduan:Pengaduan)

        fun onDetail(pengaduan: Pengaduan)
        fun onVerifikasi(pengaduan:Pengaduan)
    }
}