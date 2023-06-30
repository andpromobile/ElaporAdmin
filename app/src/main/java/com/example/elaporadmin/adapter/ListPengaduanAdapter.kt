package com.example.elaporadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Pengaduan

class ListPengaduanAdapter(
    val listPengaduan:ArrayList<Pengaduan>,
    val listener:OnAdapterListener):
RecyclerView.Adapter<ListPengaduanAdapter.ListPengaduanHolder>(){
    inner class ListPengaduanHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var judulPengaduan:TextView = itemView.findViewById(R.id.judulPengaduan)
        var isiPengaduan:TextView = itemView.findViewById(R.id.isiPengaduan)
        var editPengaduan:ImageButton = itemView.findViewById(R.id.editPengaduan)
        var hapusPengaduan:ImageButton = itemView.findViewById(R.id.hapusPengaduan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPengaduanHolder {
        val view:View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_pengaduan,
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

        holder.judulPengaduan.text = pengaduan.judulpengaduan
        holder.isiPengaduan.text = pengaduan.isipengaduan

        holder.editPengaduan.setOnClickListener {
            listener.onClick(pengaduan)
        }
    }

    interface OnAdapterListener{
        fun onClick(pengaduan:Pengaduan)
    }
}