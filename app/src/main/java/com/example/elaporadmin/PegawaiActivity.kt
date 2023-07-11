package com.example.elaporadmin

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elaporadmin.ViewModel.PegawaiViewModel
import com.example.elaporadmin.adapter.ListLokasiAdapter
import com.example.elaporadmin.adapter.ListPegawaiAdapter
import com.example.elaporadmin.dao.Bidang
import com.example.elaporadmin.dao.Lokasi
import com.example.elaporadmin.dao.Pegawai
import com.example.elaporadmin.databinding.ActivityPegawaiBinding
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PegawaiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPegawaiBinding
    private lateinit var listPegawaiAdapter: ListPegawaiAdapter
    private lateinit var rvPegawai: RecyclerView
    private lateinit var fabPegawai: FloatingActionButton
    private lateinit var tvNoPegawai: TextView
    private lateinit var pegawaiViewModel: PegawaiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPegawaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormPegawai()
    }

    private fun initLayout() {
        tvNoPegawai = binding.noPegawai

        rvPegawai.apply {
            binding.listPegawai
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
            }

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
            putExtra("MODE","EDIT")
        }

        startActivity(intent)
    }

    private fun hapusData(pegawai: Pegawai){
        val dialogBinding = layoutInflater.inflate(R.layout.my_custom_dialog,null)
        val dialog = Dialog(this@PegawaiActivity)

        with(dialog){
            setContentView(dialogBinding)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }

        val yesButton = dialogBinding.findViewById<Button>(R.id.alert_yes)
        val cancelButton = dialogBinding.findViewById<Button>(R.id.alert_cancel)

        cancelButton.setOnClickListener{
            dialog.dismiss()
        }

        yesButton.setOnClickListener{
            pegawaiViewModel.deletePegawai(pegawai.NIP)

            pegawaiViewModel.observePesanLiveData().observe(
                this@PegawaiActivity
            )
            {
                dialog.dismiss()
                initLayout()

                Toast.makeText(
                    applicationContext,
                    it.toString(),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }

}