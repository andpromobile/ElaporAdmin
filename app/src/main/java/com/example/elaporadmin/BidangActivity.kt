package com.example.elaporadmin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.ViewModel.BidangViewModel
import com.example.elaporadmin.adapter.ListBidangAdapter
import com.example.elaporadmin.adapter.ListBidangAdapter.OnAdapterListener
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.databinding.ActivityBidangBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Timer


class BidangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidangBinding
    private lateinit var listBidangAdapter: ListBidangAdapter
    private lateinit var rvBidang:RecyclerView
    private lateinit var fabBidang:FloatingActionButton
    private lateinit var tvNoBidang: TextView
    private lateinit var bidangViewModel:BidangViewModel
    private var count:Int = 0
    private lateinit var timer: Timer
    private lateinit var dialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidangBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initLayout()
        toFormBidang()
    }

    private fun initLayout() {
        tvNoBidang = binding.noBidang

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
            .setTitleText("Hapus?")
            .setContentText("Yakin Ingin Menghapus Data Ini!")
            .setConfirmButton("Iya", {
                bidangViewModel.deleteBidang(bidang.id)
                it.dismissWithAnimation()

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Data Berhasil Dihapus")
                    .show()
            })
            .setCancelButtonBackgroundColor(Color.parseColor("#A5DC86"))
            .setCancelButton("Tidak", {
                it.dismissWithAnimation()
            })
            .show()

//        val dialogBinding = layoutInflater.inflate(R.layout.my_custom_dialog,null)
//        val dialog = Dialog(this@BidangActivity)
//
//        with(dialog){
//            setContentView(dialogBinding)
//            setCancelable(true)
//            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            show()
//        }
//
//        val yesButton = dialogBinding.findViewById<Button>(R.id.alert_yes)
//        val cancelButton = dialogBinding.findViewById<Button>(R.id.alert_cancel)
//
//        cancelButton.setOnClickListener{
//            dialog.dismiss()
//        }
//
//        yesButton.setOnClickListener{
//            bidangViewModel.deleteBidang(bidang.id)
//
//            bidangViewModel.observePesanLiveData().observe(
//                this@BidangActivity
//            )
//            {
//                dialog.dismiss()
//                initLayout()
//
//                Toast.makeText(
//                    applicationContext,
//                    it.toString(),
//                    Toast.LENGTH_LONG,
//                ).show()
//            }
//        }
    }

}