package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.example.elaporadmin.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var btnBidang: CardView
    private lateinit var btnLokasi: CardView
    private lateinit var btnKelurahan: CardView
    private lateinit var btnKecamatan: CardView
    private lateinit var btnPerangkatDesa: CardView
    private lateinit var btnPegawai: CardView
    private lateinit var anyChartView: AnyChartView
//    private lateinit var btnPengaduan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadBidang()
        loadKelurahan()
        loadPerangkatDesa()
        loadPegawai()
        loadChart()
        loadLokasi()
        loadKecamatan()
//        loadPengaduan()
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
        anyChartView = binding.anyChartView

        val pie = AnyChart.pie()



        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Disetujui", 6371664))
        data.add(ValueDataEntry("Ditunda", 789622))
        data.add(ValueDataEntry("Ditolak", 7216301))

        pie.data(data)

//        pie.title("Fruits imported in 2015 (in kg)")

        pie.labels().position("outside")

        pie.legend().title().enabled(true)
        pie.legend().title()
            .text("Pengaduan")
            .padding(0.0, 0.0, 10.0, 0.0)

        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)

        anyChartView.setChart(pie)
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
}