package com.example.elaporadmin.pegawai

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
import com.example.elaporadmin.databinding.ActivityPegawaiBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PegawaiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPegawaiBinding
    private lateinit var listPegawaiAdapter: ListPegawaiAdapter
    private lateinit var rvPegawai: RecyclerView
    private lateinit var fabPegawai: FloatingActionButton
    private lateinit var tvNoPegawai: TextView
    private lateinit var pegawaiViewModel: PegawaiViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPegawaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormPegawai()
    }

    private fun initLayout() {
        progressBar = binding.progressBar
        tvNoPegawai = binding.noPegawai

        setSupportActionBar(binding.toolbarPegawai)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        rvPegawai = binding.listPegawai
        rvPegawai.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }

        pegawaiViewModel = ViewModelProvider(this)[PegawaiViewModel::class.java]
        pegawaiViewModel.getPegawai()
        pegawaiViewModel.observePegawaiLiveData().observe(
            this
        ){ pegawaiList ->
            listPegawaiAdapter = ListPegawaiAdapter(
                pegawaiList as ArrayList<Pegawai>,
                object : ListPegawaiAdapter.OnAdapterListener{
                    override fun onUpdate(pegawai: Pegawai) {
                        updateData(pegawai)
                    }

                    override fun onDelete(pegawai: Pegawai) {
                        hapusData(pegawai)
                    }

                }
            )

            rvPegawai.adapter = listPegawaiAdapter

            if(listPegawaiAdapter.itemCount <= 0){
                rvPegawai.visibility = View.GONE
                tvNoPegawai.visibility = View.VISIBLE
            }else{
                rvPegawai.visibility = View.VISIBLE
                tvNoPegawai.visibility = View.GONE
            }
            showLoading(false)

        }
    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun toFormPegawai() {
        fabPegawai = binding.fabPegawai

        fabPegawai.setOnClickListener {
            val intent = Intent(
                this@PegawaiActivity,
                PegawaiFormActivity::class.java)

            intent.putExtra("MODE","INSERT")
            startActivity(intent)
        }
    }

    private fun updateData(pegawai: Pegawai){
        val intent = Intent(
            this@PegawaiActivity,
            PegawaiFormActivity::class.java
        )

        with(intent) {
            putExtra("NIP",pegawai.NIP)
            putExtra("NAMAPEGAWAI",pegawai.namapegawai)
            putExtra("JABATAN", pegawai.jabatan)
            putExtra("BIDANG_ID", pegawai.bidang_id)
            putExtra("EMAIL", pegawai.email)
            putExtra("MODE","EDIT")
        }

        startActivity(intent)
    }

    private fun hapusData(pegawai: Pegawai){
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Hapus?")
            .setContentText("Yakin Ingin Menghapus Data Ini!")
            .setConfirmButton("Iya", {
                pegawaiViewModel.deletePegawai(pegawai.NIP)
                it.dismissWithAnimation()

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Data Berhasil Dihapus")
                    .show()
                onStart()
            })
            .setCancelButtonBackgroundColor(Color.parseColor("#A5DC86"))
            .setCancelButton("Tidak", {
                it.dismissWithAnimation()
            })
            .show()
    }


    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    

}