package com.example.elaporadmin.bidang

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.bidang.ListBidangAdapter.OnAdapterListener
import com.example.elaporadmin.databinding.ActivityBidangBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class BidangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidangBinding
    private lateinit var listBidangAdapter: ListBidangAdapter
    private lateinit var rvBidang:RecyclerView
    private lateinit var fabBidang:FloatingActionButton
    private lateinit var tvNoBidang: TextView
    private lateinit var bidangViewModel: BidangViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidangBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initLayout()
        toFormBidang()

        binding.nestedScrollView.setOnScrollChangeListener {
                v:NestedScrollView, _:Int, scrollY:Int, _:Int, _:Int ->

            if (scrollY == v?.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
//                if (viewModel.page <= viewModel.total && viewModel.loadMore.value == false) viewModel.fetch()
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Data Berhasil Dihapus")
                    .show()
            }
        }
    }

    private fun initLayout() {
        progressBar = binding.progressBar
        tvNoBidang = binding.noBidang

        setSupportActionBar(binding.toolbarBidang)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        rvBidang = binding.listBidang
        rvBidang.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(applicationContext)
        }

        bidangViewModel = ViewModelProvider(this)[BidangViewModel::class.java]
        bidangViewModel.getBidang()
        bidangViewModel.observeBidangLiveData().observe(
            this
        ) { bidangList ->
            listBidangAdapter = ListBidangAdapter(
                bidangList as ArrayList<Bidang>,
                object : OnAdapterListener {
                    override fun onUpdate(bidang: Bidang) {
                        updateData(bidang)
                    }

                    override fun onDelete(bidang: Bidang) {
                        hapusData(bidang)
                    }
                },
            )

            rvBidang.adapter = listBidangAdapter

            if (listBidangAdapter.itemCount <= 0) {

                rvBidang.visibility = View.GONE
                tvNoBidang.visibility = View.VISIBLE
            }else{
                rvBidang.visibility = View.VISIBLE
                tvNoBidang.visibility = View.GONE
            }
            showLoading(false)

        }
    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun toFormBidang(){
        fabBidang = binding.fabBidang

        fabBidang.setOnClickListener {
            val intent = Intent(
                this@BidangActivity,
                BidangFormActivity::class.java)

            intent.putExtra("MODE","INSERT")
            startActivity(intent)
        }
    }

    private fun updateData(bidang: Bidang){
        val intent = Intent(
            this@BidangActivity,
            BidangFormActivity::class.java,
        )

        with(intent) {
            putExtra("ID",bidang.id)
            putExtra("NAMABIDANG",bidang.namabidang)
            putExtra("SEKSI", bidang.seksi)
            putExtra("MODE","EDIT")
        }
        startActivity(intent)
    }

    private fun hapusData(bidang: Bidang){

        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("HAPUS?")
            .setContentText("Yakin Ingin Menghapus Data !")
            .setConfirmButton("blue", null)
            .setConfirmButtonBackgroundColor(Color.BLUE)
            .setConfirmButton("Iya") {
                bidangViewModel.deleteBidang(bidang.id)
                it.dismissWithAnimation()

                listBidangAdapter.notifyDataSetChanged()

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Data Berhasil Dihapus")
                    .show()

                onStart()
            }
            .setCancelButton("red", null)
            .setCancelButtonBackgroundColor(Color.RED)
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