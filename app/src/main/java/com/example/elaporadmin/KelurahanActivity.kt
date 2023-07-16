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
import com.example.elaporadmin.ViewModel.KelurahanViewModel
import com.example.elaporadmin.adapter.ListKelurahanAdapter
import com.example.elaporadmin.dao.Kelurahan
import com.example.elaporadmin.databinding.ActivityKelurahanBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class KelurahanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityKelurahanBinding
    private lateinit var listKelurahanAdapter: ListKelurahanAdapter
    private lateinit var rvKelurahan:RecyclerView
    private lateinit var fabKelurahan:FloatingActionButton
    private lateinit var tvNoKelurahan:TextView
    private lateinit var kelurahanViewModel: KelurahanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelurahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormKelurahan()

    }

    private fun initLayout() {
        tvNoKelurahan = binding.noKelurahan

        rvKelurahan = binding.listKelurahan
        rvKelurahan.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
        }


        kelurahanViewModel = ViewModelProvider(this)[KelurahanViewModel::class.java]
        kelurahanViewModel.apply {
            getKelurahan()
            observeKelurahanLiveData().observe(
                this@KelurahanActivity
            ) { kelurahanList ->
                listKelurahanAdapter = ListKelurahanAdapter(
                    kelurahanList as ArrayList<Kelurahan>,
                    object : ListKelurahanAdapter.OnAdapterListener {
                        override fun onUpdate(kelurahan: Kelurahan) {
                           updateData(kelurahan)
                        }

                        override fun onDelete(kelurahan: Kelurahan) {
                            hapusData(kelurahan)
                        }
                    },
                )
                rvKelurahan.adapter = listKelurahanAdapter

                if (listKelurahanAdapter.itemCount <= 0) {
                    rvKelurahan.visibility = View.GONE
                    tvNoKelurahan.visibility = View.VISIBLE
                }else{
                    rvKelurahan.visibility = View.VISIBLE
                    tvNoKelurahan.visibility = View.GONE
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initLayout()
    }
    private fun toFormKelurahan() {
        fabKelurahan = binding.fabKelurahan

        fabKelurahan.setOnClickListener {
            val intent = Intent(
                this@KelurahanActivity,
                KelurahanFormActivity::class.java)

            intent.putExtra("MODE", "INSERT")
            startActivity(intent)
        }
    }

    private fun updateData(kelurahan: Kelurahan){
        val intent = Intent(
            this@KelurahanActivity,
            KelurahanFormActivity::class.java,
        )

        with(intent) {
            putExtra("ID",kelurahan.id)
            putExtra("NAMAKELURAHAN",kelurahan.namakelurahan)
            putExtra("NAMAKECAMATAN", kelurahan.namakecamatan)
            putExtra("MODE","EDIT")
        }

        startActivity(intent)
    }

    private fun hapusData(kelurahan: Kelurahan){
        val dialogBinding = layoutInflater.inflate(R.layout.my_custom_dialog,null)
        val dialog = Dialog(this@KelurahanActivity)

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
            kelurahanViewModel.deleteKelurahan(kelurahan.id)

            kelurahanViewModel.observePesanLiveData().observe(
                this@KelurahanActivity
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