package com.example.elaporadmin.seksi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.databinding.ActivitySeksiBinding
import com.example.elaporadmin.pegawai.PegawaiFormActivity

class SeksiActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySeksiBinding
    private val seksiVM: SeksiViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        inisialisasi()
        toFormSeksi()
    }

    private fun toFormSeksi() {
        binding.fabBidang.setOnClickListener {
            val intent = Intent(
                this@SeksiActivity,
                SeksiFormActivity::class.java)

            intent.putExtra("MODE","INSERT")
            startActivity(intent)
        }
    }

    private fun inisialisasi() {
        setSupportActionBar(binding.toolbarSeksi)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.listSeksi.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }

        seksiVM.getSeksi()
        seksiVM.observeSeksiLiveData().observe(
            this
        ){ seksiList ->
            val listSeksiAdapter = ListSeksiAdapter(
                seksiList as ArrayList<Seksi>,
                object : ListSeksiAdapter.OnAdapterListener{
                    override fun onUpdate(seksi: Seksi) {
                        updateData(seksi)
                    }

                    override fun onDelete(seksi: Seksi) {
                        hapusData(seksi)
                    }

                }
            )

            binding.listSeksi.adapter = listSeksiAdapter

            if(listSeksiAdapter.itemCount <= 0){
                binding.listSeksi.visibility = View.GONE
                binding.noBidang.visibility = View.VISIBLE
            }else{
                binding.listSeksi.visibility = View.VISIBLE
                binding.noBidang.visibility = View.GONE
            }
            showLoading(false)

        }
    }

    private fun updateData(seksi:Seksi){
        val intent = Intent(
            this@SeksiActivity,
            SeksiFormActivity::class.java
        )

        with(intent) {
            putExtra("SEKSIID",seksi.id)
            putExtra("SEKSI",seksi.namaseksi)
            putExtra("BIDANG_ID", seksi.bidang_id)
            putExtra("MODE","EDIT")
        }

        startActivity(intent)
    }

    private fun hapusData(seksi: Seksi){
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Hapus?")
            .setContentText("Yakin Ingin Menghapus Data Ini!")
            .setConfirmButton("Iya") {
                seksiVM.deleteSeksi(seksi.id)
                it.dismissWithAnimation()

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Data Berhasil Dihapus")
                    .show()
                onStart()
            }
            .show()
    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        inisialisasi()
    }
}