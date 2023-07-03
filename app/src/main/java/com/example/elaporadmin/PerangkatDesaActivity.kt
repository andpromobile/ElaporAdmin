package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
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
                    override fun onClick(perangkatDesa: Perangkatdesa) {
                        val intent = Intent(
                            this@PerangkatDesaActivity,
                            PerangkatDesaFormActivity::class.java,
                        )
                        startActivity(intent)
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
            val intent = Intent(this@PerangkatDesaActivity, PerangkatDesaFormActivity::class.java)
            startActivity(intent)
        }
    }

}