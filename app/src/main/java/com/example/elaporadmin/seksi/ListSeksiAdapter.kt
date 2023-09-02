package com.example.elaporadmin.seksi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R


class ListSeksiAdapter(
    private val listSeksi:ArrayList<Seksi>,
    private val listener: OnAdapterListener
):
    RecyclerView.Adapter<ListSeksiAdapter.ListSeksiHolder>() {
    inner class ListSeksiHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var id = 0
        var namaseksi: TextView = itemView.findViewById(R.id.namaSeksi)
        var bidangid:TextView = itemView.findViewById(R.id.bidangId)
        var editSeksi:ImageButton = itemView.findViewById(R.id.editSeksi)
        var hapusSeksi:ImageButton = itemView.findViewById(R.id.hapusSeksi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSeksiHolder {
        val view:View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_seksi,
                parent,
                false)
        return ListSeksiHolder(view)
    }

    override fun getItemCount(): Int = listSeksi.size

    override fun onBindViewHolder(
        holder: ListSeksiHolder,
        position: Int
    ) {
        val seksi = listSeksi[position]

        holder.id = seksi.id
        holder.namaseksi.text = seksi.namaseksi
        holder.bidangid.text = seksi.bidang_id.toString()

        holder.editSeksi.setOnClickListener{
            listener.onUpdate(seksi)
        }

        holder.hapusSeksi.setOnClickListener{
            listener.onDelete(seksi)
        }
    }

    interface OnAdapterListener{
        fun onUpdate(seksi:Seksi)

        fun onDelete(seksi:Seksi)
    }
}