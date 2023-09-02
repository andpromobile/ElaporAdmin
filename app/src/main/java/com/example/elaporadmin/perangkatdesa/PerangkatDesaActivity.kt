package com.example.elaporadmin.perangkatdesa

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
import com.example.elaporadmin.databinding.ActivityPerangkatDesaBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PerangkatDesaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerangkatDesaBinding
    private lateinit var listPerangkatDesaAdapter: ListPerangkatDesaAdapter
    private lateinit var rvPerangkatdesa: RecyclerView
    private lateinit var fabPerangkatDesa: FloatingActionButton
    private lateinit var tvNoPerangkatDesa: TextView
    private lateinit var perangkatDesaViewModel: PerangkatDesaViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerangkatDesaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormPerangkatDesa()
    }

    private fun initLayout() {
        progressBar = binding.progressBar
        tvNoPerangkatDesa = binding.noPerangkatDesa

        setSupportActionBar(binding.toolbarPD)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        rvPerangkatdesa = binding.listPerangkatDesa

        rvPerangkatdesa.apply {

            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }

        perangkatDesaViewModel = ViewModelProvider(this)[PerangkatDesaViewModel::class.java]
        perangkatDesaViewModel.getPerangkatDesa()
        perangkatDesaViewModel.observePerangkatDesaLiveData().observe(
            this
        ){perangkatDesaList->
            listPerangkatDesaAdapter = ListPerangkatDesaAdapter(
                perangkatDesaList as ArrayList<Perangkatdesa>,
                object : ListPerangkatDesaAdapter.OnAdapterListener{
                    override fun onUpdate(perangkatDesa: Perangkatdesa) {
                        updateData(perangkatDesa)
                    }

                    override fun onDelete(perangkatDesa: Perangkatdesa) {
                        hapusData(perangkatDesa)
                    }

                }
            )

            rvPerangkatdesa.adapter = listPerangkatDesaAdapter

            if (listPerangkatDesaAdapter.itemCount <= 0){
                rvPerangkatdesa.visibility = View.GONE
                tvNoPerangkatDesa.visibility = View.VISIBLE
            }else{
                rvPerangkatdesa.visibility = View.VISIBLE
                tvNoPerangkatDesa.visibility = View.GONE
            }
            showLoading(false)
        }
    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun toFormPerangkatDesa() {
        fabPerangkatDesa = binding.fabPerangkatDesa

        fabPerangkatDesa.setOnClickListener {
            val intent = Intent(
                this@PerangkatDesaActivity,
                PerangkatDesaFormActivity::class.java
            )
            intent.putExtra("MODE","INSERT")
            startActivity(intent)
        }
    }

    private fun updateData(perangkatdesa: Perangkatdesa){
        val intent = Intent(
            this@PerangkatDesaActivity,
            PerangkatDesaFormActivity::class.java,
        )

        with(intent) {
            putExtra("ID",perangkatdesa.nik)
            putExtra("NAMAPD",perangkatdesa.namapd)
            putExtra("KECAMATANID", perangkatdesa.kecamatan_id)
            putExtra("EMAIL", perangkatdesa.email)
            putExtra("PASSWORD", perangkatdesa.password)
            putExtra("MODE","EDIT")
        }

        startActivity(intent)
    }

    private fun hapusData(perangkatdesa: Perangkatdesa){
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Hapus?")
            .setContentText("Yakin Ingin Menghapus Data Ini!")
            .setConfirmButton("Iya") {
                perangkatDesaViewModel.deletePerangkatDesa(perangkatdesa.nik)
                it.dismissWithAnimation()

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Data Berhasil Dihapus")
                    .show()

                onStart()
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