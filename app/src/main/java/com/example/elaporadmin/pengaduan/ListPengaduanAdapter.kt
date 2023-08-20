package com.example.elaporadmin.pengaduan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elaporadmin.R

class ListPengaduanAdapter(
    val listPengaduan:ArrayList<Pengaduan>,
    val listener: OnAdapterListener
):
RecyclerView.Adapter<ListPengaduanAdapter.ListPengaduanHolder>(){
    companion object{
        private val url ="https://pupr.hstkab.go.id/elapor/elapor/public/foto/"
    }
    inner class ListPengaduanHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var btnVerifikasi: Button = itemView.findViewById(R.id.btnVerifikasiPengaduanPegawai)
        var btnDetail:CardView = itemView.findViewById(R.id.cvPengaduanPegawai)
        var lokasiPengaduan:TextView = itemView.findViewById(R.id.lokasiPengaduanPegawai)
        var judulPengaduan:TextView = itemView.findViewById(R.id.judulPengaduanPegawai)
        var tanggalPengaduan:TextView = itemView.findViewById(R.id.tanggalPengaduanPegawai)
        var imageView: ImageView = itemView.findViewById(R.id.ivPengaduanPegawai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPengaduanHolder {
        val view:View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_pengaduan1,
                parent,
                false
            )

        return ListPengaduanHolder(view)
    }

    override fun getItemCount(): Int = listPengaduan.size

    override fun onBindViewHolder(
        holder: ListPengaduanHolder,
        position: Int
    ) {
        val pengaduan = listPengaduan[position]

        holder.judulPengaduan.text = pengaduan.judulpengaduan
        holder.lokasiPengaduan.text = pengaduan.datalokasi
        holder.tanggalPengaduan.text = pengaduan.tanggalpengaduan

        var foto = pengaduan.foto

        Glide
            .with(holder.itemView.context)
            .load(url +foto)
            .centerCrop()
            .into(holder.imageView)

        holder.btnDetail.setOnClickListener {
            listener.onDetail(pengaduan)
        }

        holder.btnVerifikasi.setOnClickListener {
            listener.onVerifikasi(pengaduan)
        }
    }

    interface OnAdapterListener{
        fun onClick(pengaduan: Pengaduan)

        fun onDetail(pengaduan: Pengaduan)
        fun onVerifikasi(pengaduan: Pengaduan)
    }
}