package com.example.elaporadmin.lokasi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.lokasi.ListLokasiAdapter.OnAdapterListener
import com.example.elaporadmin.databinding.ActivityLokasiBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LokasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLokasiBinding
    private lateinit var listLokasiAdapter: ListLokasiAdapter
    private lateinit var rvLokasi: RecyclerView
    private lateinit var fabLokasi: FloatingActionButton
    private lateinit var tvNoLokasi: TextView
    private lateinit var lokasiViewModel: LokasiViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormLokasi()

        binding.nsvLokasi.setOnScrollChangeListener {
                v: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == (v?.getChildAt(0)?.measuredHeight ?: 0) - (v?.measuredHeight ?: 0)) {
                showLoading(true)
                lokasiViewModel.page++
                Log.d("OnScrollChangeListener","OnScrollChangeListener dijalankan"+lokasiViewModel.page.toString())
                lokasiViewModel.getLokasiByPage()
                showLoading(false)
            }

//            if (scrollY == 0){
//                showLoading(true)
//                if (lokasiViewModel.page > 1) lokasiViewModel.page--
//                lokasiViewModel.getLokasiByPage()
//                showLoading(false)
//            }
        }

    }

    private fun firstPage(){
        binding.nsvLokasi.scrollTo(0, 0)
        lokasiViewModel.page = 1
        lokasiViewModel.getLokasiByPage()
    }

    private fun initLayout() {
        progressBar = binding.progressBar
        tvNoLokasi = binding.noLokasi
        rvLokasi = binding.listLokasi

        rvLokasi.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(applicationContext)
        }

        lokasiViewModel = ViewModelProvider(this)[LokasiViewModel::class.java]

        lokasiViewModel.getLokasiByPage()

        lokasiViewModel.observeLokasiLiveData().observe(
            this
        ){lokasiList->
            listLokasiAdapter = ListLokasiAdapter(
                arrayListOf(),
                object: OnAdapterListener {
                    override fun onUpdate(lokasi: Lokasi) {
                        updateData(lokasi)
                    }

                    override fun onDelete(lokasi: Lokasi) {
                        hapusData(lokasi)
                    }
                },
            )

            rvLokasi.adapter = listLokasiAdapter
            if (lokasiViewModel.page == 1) listLokasiAdapter.clear()
            listLokasiAdapter.addList(lokasiList as ArrayList<Lokasi>)

            if (listLokasiAdapter.itemCount <= 0){
                rvLokasi.visibility = View.GONE
                tvNoLokasi.visibility = View.VISIBLE
            }else{
                rvLokasi.visibility = View.VISIBLE
                tvNoLokasi.visibility = View.GONE
            }
            showLoading(false)
        }


    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun toFormLokasi() {
        fabLokasi = binding.fabLokasi

        fabLokasi.setOnClickListener {
            val intent = Intent(
                this@LokasiActivity,
                LokasiFormActivity::class.java)
            intent.putExtra("MODE","INSERT")
            startActivity(intent)
        }
    }

    private fun updateData(lokasi: Lokasi){
        val intent = Intent(
            this@LokasiActivity,
            LokasiFormActivity::class.java
        )

        with(intent) {
            putExtra("ID",lokasi.id)
            putExtra("DATALOKASI",lokasi.datalokasi)
            putExtra("LATITUDE", lokasi.latitude)
            putExtra("LONGITUDE", lokasi.longitude)
            putExtra("FOTO", lokasi.foto)
            putExtra("MODE","EDIT")
        }

        startActivity(intent)
    }

    private fun hapusData(lokasi: Lokasi){
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Hapus?")
            .setContentText("Yakin Ingin Menghapus Data Ini!")
            .setConfirmButton("Iya") {
                lokasiViewModel.deleteLokasi(lokasi.id)
                it.dismissWithAnimation()

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Data Berhasil Dihapus")
                    .show()
            }
            .setCancelButtonBackgroundColor(Color.parseColor("#A5DC86"))
            .setCancelButton("Tidak") {
                it.dismissWithAnimation()
            }
            .show()
    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }


}