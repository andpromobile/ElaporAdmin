package com.example.elaporadmin.pegawai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.elaporadmin.R

/**
 * A simple [Fragment] subclass.
 * Use the [PegawaiDashboardFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class PegawaiDashboardFragment2 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pegawai_dashboard2, container, false)
    }


}