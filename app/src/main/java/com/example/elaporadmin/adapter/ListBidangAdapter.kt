package com.example.elaporadmin.adapter

import android.R.attr.data
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.BidangFormActivity
import com.example.elaporadmin.R
import com.example.elaporadmin.dao.Bidang


class ListBidangAdapter(val listBidang:ArrayList<Bidang>, val listener:OnAdapterListener): RecyclerView.Adapter<ListBidangAdapter.ListBidangHolder>() {
    inner class ListBidangHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var namaBidang: TextView = itemView.findViewById(R.id.namaBidang)
        var seksi:TextView = itemView.findViewById(R.id.seksi)
        var editBidang:ImageButton = itemView.findViewById(R.id.editBidang)
        var hapusBidang:ImageButton = itemView.findViewById(R.id.hapusBidang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBidangHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_bidang, parent, false)
        return ListBidangHolder(view)
    }

    override fun getItemCount(): Int = listBidang.size

    override fun onBindViewHolder(holder: ListBidangHolder, position: Int) {
        var bidang = listBidang[position]

        holder.namaBidang.text = bidang.namabidang
        holder.seksi.text = bidang.seksi

        holder.editBidang.setOnClickListener{
            listener.onClick(bidang)
        }
    }

    fun setData(data: ArrayList<Bidang>){
        listBidang.clear()
        listBidang.addAll( data )
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(bidang:Bidang)
    }
}