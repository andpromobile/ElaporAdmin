package com.example.elaporadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Perangkatdesa

class ListPerangkatDesaAdapter(
    private val listPerangkatDesa:ArrayList<Perangkatdesa>,
    private val listener:OnAdapterListener):
    RecyclerView.Adapter<ListPerangkatDesaAdapter.ListPerangkatDesaHolder>(){

    inner class ListPerangkatDesaHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var nikPd:TextView = itemView.findViewById(R.id.nikPd)
        var namaPerangkatDesa:TextView = itemView.findViewById(R.id.namaPerangkatDesa)
        var perangkatDesaKelurahanId:TextView = itemView.findViewById(R.id.perangkatDesaKelurahanId)
        var editPerangkatDesa:ImageButton = itemView.findViewById(R.id.editPerangkatDesa)
        var hapusPerangkatdesa:ImageButton = itemView.findViewById(R.id.hapusPerangkatDesa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPerangkatDesaHolder {
       val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_perangkat_desa,
            parent, false)
        return ListPerangkatDesaHolder(view)
    }

    override fun getItemCount(): Int = listPerangkatDesa.size

    override fun onBindViewHolder(holder: ListPerangkatDesaHolder, position: Int) {
        val perangkatDesa = listPerangkatDesa[position]

        holder.nikPd.text = perangkatDesa.nik
        holder.namaPerangkatDesa.text = perangkatDesa.namapd
        holder.perangkatDesaKelurahanId.text = perangkatDesa.kelurahan_id.toString()

        holder.editPerangkatDesa.setOnClickListener {
            listener.onUpdate(perangkatDesa)
        }

        holder.hapusPerangkatdesa.setOnClickListener {
            listener.onDelete(perangkatDesa)
        }
    }

    interface OnAdapterListener{
        fun onUpdate(perangkatDesa:Perangkatdesa)
        fun onDelete(perangkatDesa: Perangkatdesa)
    }
}