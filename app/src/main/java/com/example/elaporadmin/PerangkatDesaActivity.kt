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
import com.example.elaporadmin.ViewModel.PerangkatDesaViewModel
import com.example.elaporadmin.adapter.ListPerangkatDesaAdapter
import com.example.elaporadmin.dao.Perangkatdesa
import com.example.elaporadmin.databinding.ActivityPerangkatDesaBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PerangkatDesaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerangkatDesaBinding
    private lateinit var listPerangkatDesaAdapter: ListPerangkatDesaAdapter
    private lateinit var rvPerangkatdesa: RecyclerView
    private lateinit var fabPerangkatDesa: FloatingActionButton
    private lateinit var tvNoPerangkatDesa: TextView
    private lateinit var perangkatDesaViewModel: PerangkatDesaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerangkatDesaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        toFormPerangkatDesa()
    }

    private fun initLayout() {
        tvNoPerangkatDesa = binding.noPerangkatDesa

        rvPerangkatdesa.apply {
            binding.listPerangkatDesa
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
                object :ListPerangkatDesaAdapter.OnAdapterListener{
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
            }

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
            startActivity(intent)
        }
    }

    private fun updateData(perangkatdesa: Perangkatdesa){
        val intent = Intent(
            this@PerangkatDesaActivity,
            PerangkatDesaFormActivity::class.java,
        )

        with(intent) {
            putExtra("ID",perangkatdesa.id)
            putExtra("NAMAPD",perangkatdesa.namapd)
            putExtra("KELURAHAN_ID", perangkatdesa.kelurahan_id)
            putExtra("MODE","EDIT")
        }

        startActivity(intent)
    }

    private fun hapusData(perangkatdesa: Perangkatdesa){
        val dialogBinding = layoutInflater.inflate(R.layout.my_custom_dialog,null)
        val dialog = Dialog(this@PerangkatDesaActivity)

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
            perangkatDesaViewModel.deletePerangkatDesa(perangkatdesa.id)

            perangkatDesaViewModel.observePesanLiveData().observe(
                this@PerangkatDesaActivity
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