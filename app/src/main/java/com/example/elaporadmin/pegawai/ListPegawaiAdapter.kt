package com.example.elaporadmin.pegawai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.R

class ListPegawaiAdapter(
    private val listPegawai:ArrayList<Pegawai>,
    private val listener: OnAdapterListener
):
    RecyclerView.Adapter<ListPegawaiAdapter.ListPegawaiHolder>(){
    inner class ListPegawaiHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var pegawaiNip:TextView = itemView.findViewById(R.id.pegawaiNIP)
        var pegawaiNama:TextView = itemView.findViewById(R.id.pegawaiNama)
//        var pegawaiJabatan:TextView = itemView.findViewById(R.id.frmJabatan)
//        var pegawaiBidangId:TextView = itemView.findViewById(R.id.frmBidangIdPegawai)

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

        holder.pegawaiNip.text = pegawai.NIP
        holder.pegawaiNama.text = pegawai.namapegawai
//        holder.pegawaiJabatan.text = pegawai.jabatan
//        holder.pegawaiBidangId.text = pegawai.bidang_id.toString()

        holder.editPegawai.setOnClickListener{
            listener.onUpdate(pegawai)
        }

        holder.hapusPegawai.setOnClickListener {
            listener.onDelete(pegawai)
        }
    }

    interface OnAdapterListener{
        fun onUpdate(pegawai: Pegawai)
        fun onDelete(pegawai: Pegawai)
    }
}