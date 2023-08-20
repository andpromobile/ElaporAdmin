package com.example.elaporadmin.perangkatdesa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.elaporadmin.R
import com.example.elaporadmin.databinding.FragmentPerangkatDesa1Binding
import com.example.elaporadmin.pengaduan.DetailPengaduanActivity
import com.example.elaporadmin.pengaduan.ListPengaduanAdapter
import com.example.elaporadmin.pengaduan.Pengaduan
import com.example.elaporadmin.pengaduan.PengaduanViewModel


class PerangkatDesaFragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoPengaduan: TextView
    private lateinit var listPengaduan: RecyclerView
    private lateinit var pengaduanViewModel: PengaduanViewModel
    private lateinit var listPengaduanAdapter: ListPengaduanAdapter
    private lateinit var binding:FragmentPerangkatDesa1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerangkatDesa1Binding.inflate(layoutInflater, container, false)
        initLayout()

        return binding.root
    }

    private fun initLayout(){
        progressBar = binding.progressBarPengaduanPD
        tvNoPengaduan = binding.noPengaduanPD
        listPengaduan = binding.listPengaduanPD

        val bundle = arguments
        val kecamatanid = bundle!!.getInt("KECAMATANID")

        listPengaduan.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        pengaduanViewModel = ViewModelProvider(this)[PengaduanViewModel::class.java]

        pengaduanViewModel.getPengaduanByKecamatanId(kecamatanid)

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
                            putExtra("LOKASI", pengaduan.lokasi_id)
                            putExtra("KECAMATAN", pengaduan.kecamatan_id)
                            putExtra("KELURAHAN", pengaduan.kelurahan_id)
                            putExtra("BIDANG", pengaduan.bidang_id)
                            putExtra("TANGGAL", pengaduan.tanggalpengaduan)
                            putExtra("STATUS", pengaduan.status)
                            putExtra("FOTO", pengaduan.foto)
                        }

                        startActivity(intent)
                        Animatoo.animateZoom(context!!) //fire the zoom animation


                    }

                    override fun onVerifikasi(pengaduan: Pengaduan) {
                        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setContentText("Apakah Anda Yakin?")
                            .setConfirmButton("Iya", {

                            })
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