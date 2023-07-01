package com.example.elaporadmin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.ViewModel.BidangViewModel
import com.example.elaporadmin.adapter.ListBidangAdapter
import com.example.elaporadmin.adapter.ListBidangAdapter.OnAdapterListener
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.databinding.ActivityBidangBinding
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BidangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidangBinding
    private lateinit var listBidangAdapter: ListBidangAdapter
    private lateinit var rvBidang:RecyclerView
    private lateinit var fabBidang:FloatingActionButton
    private lateinit var tvNoBidang: TextView
    private lateinit var bidangViewModel:BidangViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidangBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initLayout()
        toFormBidang()
    }

    private fun initLayout() {
        rvBidang = binding.listBidang
        rvBidang.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }

        bidangViewModel = ViewModelProvider(this)[BidangViewModel::class.java]
        bidangViewModel.getBidang()
        bidangViewModel.observeBidangLiveData().observe(
            this,
            Observer { bidangList ->
                listBidangAdapter = ListBidangAdapter(
                    bidangList as ArrayList<Bidang>,
                    object : OnAdapterListener {
                        override fun onClick(bidang: Bidang) {
                            val intent = Intent(
                                this@BidangActivity,
                                BidangFormActivity::class.java,
                            )
                            startActivity(intent)
                        }
                    },
                )

                rvBidang.adapter = listBidangAdapter

                if (listBidangAdapter.itemCount <= 0){
                    rvBidang.visibility = View.GONE
                    tvNoBidang.visibility = View.VISIBLE
                }

            })


    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }

    private fun toFormBidang(){
        fabBidang = binding.fabBidang

        fabBidang.setOnClickListener {
            val intent = Intent(this@BidangActivity,
                BidangFormActivity::class.java)
            startActivity(intent)
        }
    }

}