package com.example.elaporadmin.pengaduan

import android.graphics.Color
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
        var btnTolak: Button = itemView.findViewById(R.id.btnTolakPengaduanPegawai)
        var btnDetail:CardView = itemView.findViewById(R.id.cvPengaduanLain)
        var lokasiPengaduan:TextView = itemView.findViewById(R.id.lokasiPengaduanLain)
        var judulPengaduan:TextView = itemView.findViewById(R.id.judulPengaduanLain)
        var tanggalPengaduan:TextView = itemView.findViewById(R.id.tanggalPengaduanLain)
        var statusPengaduan:TextView = itemView.findViewById(R.id.statusPengaduanLain)
        var imageView: ImageView = itemView.findViewById(R.id.ivPengaduanLain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPengaduanHolder {
        val view:View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_pengaduan_pegawai,
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

        when(pengaduan.status){
            "0" -> {
                holder.statusPengaduan.text = "Menunggu Verifikasi Perangkat Desa"
                holder.statusPengaduan.setTextColor(Color.parseColor("#900C3F"))
            }
            "1"-> {
                holder.statusPengaduan.text = "Telah Diverifikasi Perangkat Desa"
                holder.statusPengaduan.setTextColor(Color.parseColor("#F94C10"))

                holder.btnVerifikasi.visibility = View.VISIBLE
                holder.btnVerifikasi.text = "Verifikasi"
            }
            "2"-> {
                holder.statusPengaduan.text = "Diverifikasi"
                holder.statusPengaduan.setTextColor(Color.parseColor("#1A5D1A"))

                holder.btnVerifikasi.visibility = View.VISIBLE
                holder.btnVerifikasi.text = "Selesai"
            }
            "3"-> {
                holder.statusPengaduan.text = "Telah Selesai"
                holder.statusPengaduan.setTextColor(Color.parseColor("#1A5D1A"))

                holder.btnTolak.visibility = View.GONE
            }
            "4"-> {
                holder.statusPengaduan.text ="Ditolak oleh Perangkat Desa"
                holder.statusPengaduan.setTextColor(Color.parseColor("#FF0000"))
                holder.btnTolak.visibility = View.GONE
            }
        }

        var foto = pengaduan.foto

        Glide
            .with(holder.itemView.context)
            .load(url +foto)
            .centerCrop()
            .into(holder.imageView)

        holder.btnDetail.setOnClickListener {
            listener.onDetail(pengaduan)
        }

        holder.btnTolak.setOnClickListener{
            listener.onDeny(pengaduan)
        }

        holder.btnVerifikasi.setOnClickListener {
            listener.onVerifikasi(pengaduan)
        }
    }

    interface OnAdapterListener{
        fun onClick(pengaduan: Pengaduan)

        fun onDetail(pengaduan: Pengaduan)
        fun onVerifikasi(pengaduan: Pengaduan)

        fun onDeny(pengaduan: Pengaduan)
    }
}