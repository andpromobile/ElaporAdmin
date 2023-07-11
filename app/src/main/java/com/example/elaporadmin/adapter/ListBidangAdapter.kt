package com.example.elaporadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Bidang


class ListBidangAdapter(
    private val listBidang:ArrayList<Bidang>,
    private val listener:OnAdapterListener):
    RecyclerView.Adapter<ListBidangAdapter.ListBidangHolder>() {
    inner class ListBidangHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var id = 0
        var namaBidang: TextView = itemView.findViewById(R.id.namaBidang)
        var seksi:TextView = itemView.findViewById(R.id.seksi)
        var editBidang:ImageButton = itemView.findViewById(R.id.editBidang)
        var hapusBidang:ImageButton = itemView.findViewById(R.id.hapusBidang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBidangHolder {
        val view:View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_bidang,
                parent,
                false)
        return ListBidangHolder(view)
    }

    override fun getItemCount(): Int = listBidang.size

    override fun onBindViewHolder(
        holder: ListBidangHolder,
        position: Int
    ) {
        val bidang = listBidang[position]

        holder.id = bidang.id
        holder.namaBidang.text = bidang.namabidang
        holder.seksi.text = bidang.seksi

        holder.editBidang.setOnClickListener{
            listener.onUpdate(bidang)
        }

        holder.hapusBidang.setOnClickListener{
            listener.onDelete(bidang)
        }
    }

    interface OnAdapterListener{
        fun onUpdate(bidang:Bidang)

        fun onDelete(bidang: Bidang)
    }
}