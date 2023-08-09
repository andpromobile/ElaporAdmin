package com.example.elaporadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Lokasi

class ListLokasiAdapter(val listLokasi:ArrayList<Lokasi>,
                        val listener: OnAdapterListener):
    RecyclerView.Adapter<ListLokasiAdapter.ListLokasiHolder>(){

    inner class ListLokasiHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        var bidang_id: TextView = itemView.findViewById(R.id.bidang_id)
        var lokasi:TextView = itemView.findViewById(R.id.lokasi)
        var editLokasi:TextView = itemView.findViewById(R.id.editLokasi)
        var hapusLokasi:TextView = itemView.findViewById(R.id.hapusLokasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLokasiAdapter.ListLokasiHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_lokasi, parent, false)
        return ListLokasiHolder(view)
    }

    override fun getItemCount(): Int = listLokasi.size

    override fun onBindViewHolder(holder: ListLokasiAdapter.ListLokasiHolder, position: Int) {
        val lokasi = listLokasi[position]

        holder.bidang_id.text = lokasi.bidang_id.toString()
        holder.lokasi.text = lokasi.datalokasi

        holder.editLokasi.setOnClickListener{
            listener.onUpdate(lokasi)
        }

        holder.hapusLokasi.setOnClickListener{
            listener.onDelete(lokasi)
        }
    }

    interface OnAdapterListener{
        fun onUpdate(lokasi: Lokasi)
        fun onDelete(lokasi: Lokasi)
    }
}