package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.elaporadmin.bidang.BidangActivity
import com.example.elaporadmin.databinding.ActivityDashboardBinding
import com.example.elaporadmin.kecamatan.KecamatanActivity
import com.example.elaporadmin.kelurahan.KelurahanActivity
import com.example.elaporadmin.lokasi.LokasiActivity
import com.example.elaporadmin.pegawai.PegawaiActivity
import com.example.elaporadmin.pengaduan.DetailPengaduanLainActivity
import com.example.elaporadmin.pengaduan.ListPengaduanLainAdapter
import com.example.elaporadmin.pengaduan.Pengaduanlain
import com.example.elaporadmin.pengaduan.PengaduanLainViewModel
import com.example.elaporadmin.perangkatdesa.PerangkatDesaActivity
import com.example.elaporadmin.seksi.SeksiActivity
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var listPengaduanLainAdapter: ListPengaduanLainAdapter
    private lateinit var btnBidang: CardView
    private lateinit var btnLokasi: CardView
    private lateinit var btnKelurahan: CardView
    private lateinit var btnKecamatan: CardView
    private lateinit var btnPerangkatDesa: CardView
    private lateinit var btnPegawai: CardView
    private lateinit var btnLogOut:ImageButton
    private lateinit var chart:AAChartView
    private lateinit var rvPengaduanLain: RecyclerView
    private lateinit var pengaduanLainViewModel: PengaduanLainViewModel
//    private lateinit var btnPengaduan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logout()
        loadBidang()
        loadKelurahan()
        loadPerangkatDesa()
        loadPegawai()
        loadChart()
        loadLokasi()
        loadKecamatan()
        loadPengaduanLainList()
        loadSeksi()
//        loadPengaduan()
    }

    private fun loadSeksi() {
        binding.toSeksi.setOnClickListener{
            val intent = Intent(this@DashboardActivity, SeksiActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        loadPengaduanLainList()
    }

    private fun loadPengaduanLainList() {
        rvPengaduanLain = binding.listPengaduanLainDashboard

        rvPengaduanLain.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(applicationContext)
        }

        pengaduanLainViewModel = ViewModelProvider(this)[PengaduanLainViewModel::class.java]
        pengaduanLainViewModel.getPengaduanLain()

        pengaduanLainViewModel.observePengaduanLiveData().observe(
            this
        ) {
            listPengaduanLainAdapter = ListPengaduanLainAdapter(
                it as ArrayList<Pengaduanlain>,
                object : ListPengaduanLainAdapter.OnAdapterListener {
                    override fun onClick(pengaduan: Pengaduanlain) {
                        val v = ""
                    }

                    override fun onDetail(pengaduan: Pengaduanlain) {
                        val intent = Intent(
                            this@DashboardActivity,
                            DetailPengaduanLainActivity::class.java,
                        )

                        with(intent) {
                            putExtra("ID", pengaduan.id)
                            putExtra("NAMA", pengaduan.nama)
                            putExtra("TELP", pengaduan.telp)
                            putExtra("JUDUL", pengaduan.judulpengaduan)
                            putExtra("ISI", pengaduan.isipengaduan)
                            putExtra("LOKASI", pengaduan.namalokasi)
                            putExtra("KELURAHAN", pengaduan.namakelurahan)
                            putExtra("TANGGAL", pengaduan.tanggalpengaduan)
                            putExtra("STATUS", pengaduan.status)
                            putExtra("FOTO", pengaduan.foto)
                            putExtra("LATITUDE", pengaduan.latitude)
                            putExtra("LONGITUDE", pengaduan.longitude)
                        }

                        startActivity(intent)
                        Animatoo.animateZoom(this@DashboardActivity) //fire the zoom animation
                    }

                    override fun onVerifikasi(pengaduan: Pengaduanlain) {
                        val v = ""
                    }

                    override fun onDeny(pengaduan: Pengaduanlain) {
                        val v = ""
                    }

                },
            )

            rvPengaduanLain.adapter = listPengaduanLainAdapter

//            if (listBidangAdapter.itemCount <= 0) {
//
//                rvBidang.visibility = View.GONE
//                tvNoBidang.visibility = View.VISIBLE
//            }else{
//                rvBidang.visibility = View.VISIBLE
//                tvNoBidang.visibility = View.GONE
//            }
//            showLoading(false)

        }

    }

    private fun logout() {
        btnLogOut = binding.btnLogOut
        btnLogOut.setOnClickListener {
            finish()
        }
    }

//    private fun loadPengaduan() {
//        btnPengaduan = binding.toPengaduan
//
//        btnPengaduan.setOnClickListener {
//            val intent = Intent(
//                this@DashboardActivity,
//                PengaduanFormActivity::class.java
//            )
//
//            startActivity(intent)
//        }
//
//
//    }

    private fun loadChart(){
        chart = binding.aaChartView

        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Bar)
            .title("Pengaduan")
            .subtitle("Status Pengaduan")
            .backgroundColor("#ffffff")
            .dataLabelsEnabled(true)
            .series(arrayOf(
                AASeriesElement()
                    .name("Pending")
                    .data(arrayOf(7)),
                AASeriesElement()
                    .name("Diteruskan ")
                    .data(arrayOf(8)),
                AASeriesElement()
                    .name("Diverifikasi")
                    .data(arrayOf(4)),
                AASeriesElement()
                    .name("Ditolak")
                    .data(arrayOf(6))
            )
            )

        chart.aa_drawChartWithChartModel(aaChartModel)
//        anyChartView = binding.anyChartView
//
//        val pie = AnyChart.pie()
//
//
//
//        val data: MutableList<DataEntry> = ArrayList()
//        data.add(ValueDataEntry("Disetujui", 6371664))
//        data.add(ValueDataEntry("Ditunda", 789622))
//        data.add(ValueDataEntry("Ditolak", 7216301))
//
//        pie.data(data)
//
////        pie.title("Fruits imported in 2015 (in kg)")
//
//        pie.labels().position("outside")
//
//        pie.legend().title().enabled(true)
//        pie.legend().title()
//            .text("Pengaduan")
//            .padding(0.0, 0.0, 10.0, 0.0)
//
//        pie.legend()
//            .position("center-bottom")
//            .itemsLayout(LegendLayout.HORIZONTAL)
//            .align(Align.CENTER)
//
//        anyChartView.setChart(pie)
    }

    private fun loadPegawai() {
        btnPegawai = binding.toPegawai

        btnPegawai.setOnClickListener {
            val intent = Intent(
                this@DashboardActivity,
                PegawaiActivity::class.java
            )

            startActivity(intent)
        }
    }

    private fun loadLokasi() {
        btnLokasi = binding.toLokasi

        btnLokasi.setOnClickListener {
            val intent = Intent(
                this@DashboardActivity,
                LokasiActivity::class.java
            )

            startActivity(intent)
        }
    }

    private fun loadPerangkatDesa() {
        btnPerangkatDesa = binding.toPerangkatDesa

        btnPerangkatDesa.setOnClickListener {
            val intent = Intent(this@DashboardActivity, PerangkatDesaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadKelurahan() {
        btnKelurahan = binding.toKelurahan

        btnKelurahan.setOnClickListener{
            val intent = Intent(this@DashboardActivity, KelurahanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadKecamatan() {
        btnKecamatan = binding.toKecamatan

        btnKecamatan.setOnClickListener{
            val intent = Intent(this@DashboardActivity, KecamatanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadBidang() {
        btnBidang = binding.toBidang

        btnBidang.setOnClickListener{
            val intent = Intent(this@DashboardActivity, BidangActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setContentText("Apakah Anda Ingin Keluar Dari Aplikasi?")
            .setConfirmButton("IYA") {
                finish()
            }
            .show()

    }
}