package com.example.elaporadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Kecamatan

class ListKecamatanAdapter(val listKecamatan:ArrayList<Kecamatan>,
                           val listener:OnAdapterListener): RecyclerView.Adapter<ListKecamatanAdapter.ListKecamatanHolder>() {
    inner class ListKecamatanHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var id = 0
        var namaKecamatan: TextView = itemView.findViewById(R.id.namaKecamatan)
        var editKecamatan: ImageButton = itemView.findViewById(R.id.editKecamatan)
        var hapusKecamatan: ImageButton = itemView.findViewById(R.id.hapusKecamatan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListKecamatanHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_kecamatan, parent, false)
        return ListKecamatanHolder(view)
    }

    override fun getItemCount(): Int = listKecamatan.size

    override fun onBindViewHolder(holder: ListKecamatanHolder, position: Int) {
        val kecamatan = listKecamatan[position]

        holder.id = kecamatan.id
        holder.namaKecamatan.text = kecamatan.namakecamatan

        holder.editKecamatan.setOnClickListener{
            listener.onUpdate(kecamatan)
        }

        holder.hapusKecamatan.setOnClickListener {
            listener.onDelete(kecamatan)
        }
    }


    interface OnAdapterListener{
        fun onUpdate(Kecamatan: Kecamatan)
        fun onDelete(Kecamatan: Kecamatan)
    }
}