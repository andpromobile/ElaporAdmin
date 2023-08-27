package com.example.elaporadmin.kelurahan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R

class ListKelurahanAdapter(val listKelurahan:ArrayList<Kelurahan>,
                           val listener: OnAdapterListener
): RecyclerView.Adapter<ListKelurahanAdapter.ListKelurahanHolder>() {
    inner class ListKelurahanHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var id = 0
        var namakelurahan: TextView = itemView.findViewById(R.id.namaKelurahan)
        var namakecamatan: TextView = itemView.findViewById(R.id.namaKecamatan)
        var editKelurahan: ImageButton = itemView.findViewById(R.id.editKelurahan)
        var hapusKelurahan: ImageButton = itemView.findViewById(R.id.hapusKelurahan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListKelurahanHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_kelurahan, parent, false)
        return ListKelurahanHolder(view)
    }

    override fun getItemCount(): Int = listKelurahan.size

    override fun onBindViewHolder(holder: ListKelurahanHolder, position: Int) {
        val kelurahan = listKelurahan[position]

        holder.id = kelurahan.id
        holder.namakelurahan.text = kelurahan.namakelurahan
        holder.namakecamatan.text = kelurahan.namakecamatan

        holder.editKelurahan.setOnClickListener{
            listener.onUpdate(kelurahan)
        }

        holder.hapusKelurahan.setOnClickListener {
            listener.onDelete(kelurahan)
        }
    }


    interface OnAdapterListener{
        fun onUpdate(kelurahan: Kelurahan)
        fun onDelete(kelurahan: Kelurahan)
    }
}