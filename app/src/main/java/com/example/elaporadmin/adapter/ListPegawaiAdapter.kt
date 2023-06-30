package com.example.elaporadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Pegawai

class ListPegawaiAdapter(val listPegawai:ArrayList<Pegawai>,
                         val listener:OnAdapterListener):
    RecyclerView.Adapter<ListPegawaiAdapter.ListPegawaiHolder>(){
    inner class ListPegawaiHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var pegawaiNip:TextView = itemView.findViewById(R.id.pegawaiNIP)
        var pegawaiNama:TextView = itemView.findViewById(R.id.pegawaiNama)
        var editPegawai: ImageButton = itemView.findViewById(R.id.editPegawai)
        var hapusPegawai: ImageButton = itemView.findViewById(R.id.hapusPegawai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPegawaiHolder {
       val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_pegawai,
       parent, false)
        return ListPegawaiHolder(view)
    }

    override fun getItemCount(): Int = listPegawai.size

    override fun onBindViewHolder(holder: ListPegawaiHolder, position: Int) {
       val pegawai = listPegawai[position]

        holder.pegawaiNip.text = pegawai.NIP.toString()
        holder.pegawaiNama.text = pegawai.namapegawai

        holder.editPegawai.setOnClickListener{
            listener.onClick(pegawai)
        }
    }

    interface OnAdapterListener{
        fun onClick(pegawai:Pegawai)
    }
}