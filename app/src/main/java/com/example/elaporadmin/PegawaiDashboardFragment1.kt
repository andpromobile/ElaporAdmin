package com.example.elaporadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.ViewModel.PengaduanViewModel


class PegawaiDashboardFragment1 : Fragment() {
    private lateinit var progressBar:ProgressBar
    private lateinit var tvNoPengaduan:TextView
    private lateinit var listPengaduan:RecyclerView
    private lateinit var pengaduanViewModel:PengaduanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_pegawai_dashboard1, container, false)

        initLayout(v)

        return v
    }

    private fun initLayout(v:View){
        progressBar = v.findViewById(R.id.progressBarPengaduanPegawai)
        tvNoPengaduan = v.findViewById(R.id.noPengaduanPegawai)
        listPengaduan = v.findViewById(R.id.listPengaduan1)

        listPengaduan.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }


    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

}