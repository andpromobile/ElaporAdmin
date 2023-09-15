package com.example.elaporadmin.pegawai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.elaporadmin.R
import com.example.elaporadmin.pengaduan.DetailPengaduanActivity
import com.example.elaporadmin.pengaduan.ListPengaduanAdapter
import com.example.elaporadmin.pengaduan.Pengaduan
import com.example.elaporadmin.pengaduan.PengaduanViewModel

class PegawaiDashboardFragment1 : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoPengaduan: TextView
    private lateinit var listPengaduan: RecyclerView
    private lateinit var pengaduanViewModel: PengaduanViewModel
    private lateinit var listPengaduanAdapter: ListPengaduanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_pegawai_dashboard1, container, false)
        val b = requireArguments().getInt("SEKSIID")
        Log.d("Dari Fragment", b.toString())
        initLayout(v, b)

        return v
    }

    private fun initLayout(v: View, b:Int){
        progressBar = v.findViewById(R.id.progressBarPengaduanPegawai)
        tvNoPengaduan = v.findViewById(R.id.noPengaduanPegawai)
        listPengaduan = v.findViewById(R.id.listPengaduan1)


        listPengaduan.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        pengaduanViewModel = ViewModelProvider(this)[PengaduanViewModel::class.java]

        pengaduanViewModel.getPengaduanBySeksiId(b)


        pengaduanViewModel.observePengaduanLiveData().observe(
            viewLifecycleOwner
        ){ pengaduanList->
            listPengaduanAdapter = ListPengaduanAdapter(
                pengaduanList as ArrayList<Pengaduan>,
                object : ListPengaduanAdapter.OnAdapterListener {
                    override fun onClick(pengaduan: Pengaduan) {
                        val v: String
                    }

                    override fun onDetail(pengaduan: Pengaduan) {

                        val intent = Intent(
                            requireActivity(),
                            DetailPengaduanActivity::class.java,
                        )

                        with(intent) {
                            putExtra("ID", pengaduan.id)
                            putExtra("NAMA", pengaduan.nama)
                            putExtra("TELP", pengaduan.telp)
                            putExtra("JUDUL", pengaduan.judulpengaduan)
                            putExtra("ISI", pengaduan.isipengaduan)
                            putExtra("LOKASI", pengaduan.datalokasi)
                            putExtra("KECAMATAN", pengaduan.namakecamatan)
                            putExtra("KELURAHAN", pengaduan.namakelurahan)
                            putExtra("BIDANG", pengaduan.namabidang)
                            putExtra("TANGGAL", pengaduan.tanggalpengaduan)
                            putExtra("STATUS", pengaduan.status)
                            putExtra("FOTO", pengaduan.foto)
                            putExtra("SUMBER","2")
                        }

                        startActivity(intent)
                        Animatoo.animateZoom(context!!) //fire the zoom animation


                    }

                    override fun onVerifikasi(pengaduan: Pengaduan) {
                        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setContentText("Apakah Anda Yakin?")
                            .setConfirmButton("Iya") {
                                if(pengaduan.status == "1"){
                                    pengaduan.id?.let { it1 -> pengaduanViewModel.pengaduanVerifikasi(it1) }
                                }else if(pengaduan.status == "2"){
                                    pengaduan.id?.let { it1 -> pengaduanViewModel.pengaduanVerifikasi1(it1) }
                                }
                            }
                            .show()
                    }

                    override fun onDeny(pengaduan: Pengaduan) {
                        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setContentText("Apakah Anda Yakin?")
                            .setConfirmButton("Iya") {
                                pengaduan.id?.let { it1 -> pengaduanViewModel.pengaduanDeny(it1) }
                            }
                            .show()
                    }

                }
            )

            listPengaduan.adapter = listPengaduanAdapter

            if (listPengaduanAdapter.itemCount <= 0) {
                listPengaduan.visibility = View.GONE
                tvNoPengaduan.visibility = View.VISIBLE
            }else{
                listPengaduan.visibility = View.VISIBLE
                tvNoPengaduan.visibility = View.GONE
            }
            showLoading(false)
        }

    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

}