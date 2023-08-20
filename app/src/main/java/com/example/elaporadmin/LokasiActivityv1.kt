package com.example.elaporadmin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.lokasi.LokasiViewModel
import com.example.elaporadmin.lokasi.ListLokasiAdapter
import com.example.elaporadmin.lokasi.Lokasi
import com.example.elaporadmin.lokasi.ResponseLokasi
import com.example.elaporadmin.databinding.ActivityLokasiBinding
import com.example.elaporadmin.lokasi.LokasiFormActivity
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LokasiActivityv1 : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: ActivityLokasiBinding
    private lateinit var listLokasiAdapter: ListLokasiAdapter
    private lateinit var rvLokasi: RecyclerView
    private lateinit var fabLokasi: FloatingActionButton
    private lateinit var tvNoLokasi: TextView
    private lateinit var lokasiViewModel: LokasiViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var layoutManager: LinearLayoutManager
    private var page = 1
    private var totalPage: Int = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar

        tvNoLokasi = binding.noLokasi

//        swipeRefresh = binding.swipeRefresh
//        swipeRefresh.setOnRefreshListener(this)

        rvLokasi = binding.listLokasi
        layoutManager = LinearLayoutManager(this)

//        rvLokasi.apply {
//            this.setHasFixedSize(true)
//            this.layoutManager = layoutManager
//        }

        rvLokasi.setHasFixedSize(true)
        rvLokasi.layoutManager = layoutManager

//        private fun setupRecyclerView() {
//            rvUsers.setHasFixedSize(true)
//            rvUsers.layoutManager = layoutManager
//            adapter = UsersAdapter()
//            rvUsers.adapter = adapter
//        }
//
//        setupRecyclerView()

        getLokasi(false)

        rvLokasi.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {Log.d("MainActivity", "onScrollChange: ")
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total  = listLokasiAdapter.itemCount
                if (!isLoading && page < totalPage){
                    if (visibleItemCount + pastVisibleItem>= total){
                        page++
                        getLokasi(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

//        initLayout()
        toFormLokasi()
    }

    private fun getLokasi(isOnRefresh: Boolean) {
        isLoading = true
        val translateAnimation = TranslateAnimation(0F,50F,0F,0F)
        translateAnimation.duration = 200
        translateAnimation.isFillEnabled = true
        translateAnimation.fillAfter = true
        progressBar.startAnimation(translateAnimation)
        if (!isOnRefresh) progressBar.visibility = View.VISIBLE
        Handler().postDelayed({
//            val parameters = HashMap<String, String>()
//            parameters["page"] = page.toString()
//            Log.d("PAGE", "$page")

//            ApiService.api.getLokasiPage(page).enqueue(object: Callback<ResponseLokasi>{
//                override fun onResponse(
//                    call: Call<ResponseLokasi>,
//                    response: Response<ResponseLokasi>
//                ) {
//                    totalPage = response.body()?.total_pages!!
//                    Log.d("PAGE", "totalPage: $totalPage")
//                    val listResponse = response.body()?.data
//                    if (listResponse != null){
//                        Log.d("PAGE", "listResponse != null")
////                        listLokasiAdapter = ListLokasiAdapter()
//                        listLokasiAdapter.addList(listResponse)
//
//                        rvLokasi.adapter = listLokasiAdapter
////                        listLokasiAdapter = ListLokasiAdapter(
////                            listResponse,
////                            object: OnAdapterListener {
////                                override fun onUpdate(lokasi: Lokasi) {
////
////                                    updateData(lokasi)
////                                }
////
////                                override fun onDelete(lokasi: Lokasi) {
////                                    hapusData(lokasi)
////                                }
////                            },
////                        )
//                    }
//                    if (page == totalPage){
//                        progressBar.visibility = View.GONE
//                    }else{
//                        progressBar.visibility = View.INVISIBLE
//                    }
//
////
//
//                    isLoading = false
//                    swipeRefresh.isRefreshing = false
//
//
//
//
//
//                }
//
//                override fun onFailure(call: Call<ResponseLokasi>, t: Throwable) {
//                    Toast.makeText(this@LokasiActivityv1, "${t.message}", Toast.LENGTH_SHORT).show()
//                    progressBar.visibility = View.GONE
//                    isLoading = false
//                    swipeRefresh.isRefreshing = false
//                }
//
//            })


//            RetrofitClient.instance.getUsers(parameters).enqueue(object : Callback<UsersResponse>{
//                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
//                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
//                    progressBar.visibility = View.GONE
//                    isLoading = false
//                    swipeRefresh.isRefreshing = false
//                }override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
//                    totalPage = response.body()?.total_pages!!
//                    Log.d("PAGE", "totalPage: $totalPage")
//                    val listResponse = response.body()?.data
//                    if (listResponse != null){
//                        Log.d("PAGE", "listResponse != null")
//                        adapter.addList(listResponse)
//                    }
//                    if (page == totalPage){
//                        progressBar.visibility = View.GONE
//                    }else{
//                        progressBar.visibility = View.INVISIBLE
//                    }
//                    isLoading = false
//                    swipeRefresh.isRefreshing = false
//                }
//            })
        }, 4000)
    }


//    private fun initLayout() {
//        progressBar = binding.progressBar
//
//        tvNoLokasi = binding.noLokasi
//
////        rvLokasi = binding.listLokasi
////
////        rvLokasi.apply {
////            this.setHasFixedSize(true)
////            this.layoutManager = LinearLayoutManager(applicationContext)
////        }
//
////            lokasiViewModel = ViewModelProvider(this)[LokasiViewModel::class.java]
////
////            lokasiViewModel.getLokasi()
////
////            lokasiViewModel.observeLokasiLiveData().observe(
////                this
////            ){lokasiList->
////                listLokasiAdapter = ListLokasiAdapter(
////                    lokasiList as ArrayList<Lokasi>,
////                    object: OnAdapterListener {
////                        override fun onUpdate(lokasi: Lokasi) {
////
////                            updateData(lokasi)
////                        }
////
////                        override fun onDelete(lokasi: Lokasi) {
////                            hapusData(lokasi)
////                        }
////                    },
////                )
////
////
////                rvLokasi.adapter = listLokasiAdapter
////
////                if (listLokasiAdapter.itemCount <= 0){
////                    rvLokasi.visibility = View.GONE
////                    tvNoLokasi.visibility = View.VISIBLE
////                }else{
////                    rvLokasi.visibility = View.VISIBLE
////                    tvNoLokasi.visibility = View.GONE
////                }
////                showLoading(false)
////            }
//
//
//    }

    override fun onStart() {
        super.onStart()
//        initLayout()
    }

    private fun toFormLokasi() {
        fabLokasi = binding.fabLokasi

        fabLokasi.setOnClickListener {
            val intent = Intent(
                this@LokasiActivityv1,
                LokasiFormActivity::class.java)
            intent.putExtra("MODE","INSERT")
            startActivity(intent)
        }
    }

    private fun updateData(lokasi: Lokasi){
        val intent = Intent(
            this@LokasiActivityv1,
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

    override fun onRefresh() {
        listLokasiAdapter.clear()
        page = 1
        getLokasi(true)
    }


}