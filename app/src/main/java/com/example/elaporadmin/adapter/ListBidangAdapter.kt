package com.example.elaporadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Bidang

class ListBidangAdapter(val listBidang:ArrayList<Bidang>): RecyclerView.Adapter<ListBidangAdapter.ListBidangHolder>() {
    inner class ListBidangHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var namaBidang: TextView = itemView.findViewById(R.id.namaBidang)
        var seksi:TextView = itemView.findViewById(R.id.seksi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBidangHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_bidang, parent, false)
        return ListBidangHolder(view)
    }

    override fun getItemCount(): Int = listBidang.size

    override fun onBindViewHolder(holder: ListBidangHolder, position: Int) {
        val bidang = listBidang[position]

        holder.namaBidang.text = bidang.NamaBidang
        holder.seksi.text = bidang.Seksi
    }
}