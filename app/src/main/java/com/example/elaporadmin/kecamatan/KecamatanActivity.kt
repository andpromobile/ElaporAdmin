package com.example.elaporadmin.kecamatan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.databinding.ActivityKecamatanBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class KecamatanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKecamatanBinding
    private lateinit var listKecamatanAdapter: ListKecamatanAdapter
    private lateinit var rvKecamatan: RecyclerView
    private lateinit var fabKecamatan: FloatingActionButton
    private lateinit var tvNoKecamatan: TextView
    private lateinit var kecamatanViewModel: KecamatanViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKecamatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormKecamatan()
    }

    private fun initLayout() {
        progressBar = binding.progressBar
        tvNoKecamatan = binding.noKecamatan

        setSupportActionBar(binding.toolbarKecamatan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        rvKecamatan = binding.listKecamatan
        rvKecamatan.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }


        kecamatanViewModel = ViewModelProvider(this)[KecamatanViewModel::class.java]
        kecamatanViewModel.apply {
            getKecamatan()
            observeKecamatanLiveData().observe(
                this@KecamatanActivity
            ) { KecamatanList ->
                listKecamatanAdapter = ListKecamatanAdapter(
                    KecamatanList as ArrayList<Kecamatan>,
                    object : ListKecamatanAdapter.OnAdapterListener {
                        override fun onUpdate(Kecamatan: Kecamatan) {
                            updateData(Kecamatan)
                        }

                        override fun onDelete(Kecamatan: Kecamatan) {
                            hapusData(Kecamatan)
                        }
                    },
                )
                rvKecamatan.adapter = listKecamatanAdapter

                if (listKecamatanAdapter.itemCount <= 0) {
                    rvKecamatan.visibility = View.GONE
                    tvNoKecamatan.visibility = View.VISIBLE
                }else{
                    rvKecamatan.visibility = View.VISIBLE
                    tvNoKecamatan.visibility = View.GONE
                }
                showLoading(false)
            }
        }
    }

    private fun toFormKecamatan() {
        fabKecamatan = binding.fabKecamatan

        fabKecamatan.setOnClickListener {
            val intent = Intent(
                this@KecamatanActivity,
                KecamatanFormActivity::class.java)

            intent.putExtra("MODE", "INSERT")
            startActivity(intent)
        }
    }

    private fun updateData(kecamatan: Kecamatan){
        val intent = Intent(
            this@KecamatanActivity,
            KecamatanFormActivity::class.java,
        )

        with(intent) {
            putExtra("ID",kecamatan.id)
            putExtra("NAMAKECAMATAN", kecamatan.namakecamatan)
            putExtra("MODE","EDIT")
        }

        startActivity(intent)
    }

    private fun hapusData(kecamatan: Kecamatan){
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Hapus?")
            .setContentText("Yakin Ingin Menghapus Data Ini!")
            .setConfirmButton("Iya") {
                kecamatanViewModel.deleteKecamatan(kecamatan.id)
                it.dismissWithAnimation()

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Data Berhasil Dihapus")
                    .show()

                onStart()
            }
            .show()
    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }
}