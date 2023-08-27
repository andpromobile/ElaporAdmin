package com.example.elaporadmin.perangkatdesa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.databinding.FragmentPerangkatDesa2Binding

class PerangkatDesaFragment2 : Fragment() {

    private lateinit var binding: FragmentPerangkatDesa2Binding
    private lateinit var nama:TextView
    private lateinit var level:TextView
    private lateinit var email:EditText
    private lateinit var password:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerangkatDesa2Binding.inflate(layoutInflater, container, false)
        initLayout()

        return binding.root
    }

    private fun initLayout() {
        val nik = requireArguments().getString("NIK")
        nama = binding.nama
        level = binding.level
        email = binding.frmEmail
        password = binding.frmPassword
        Log.d("DAri fragmetn 2", "DAri fragmetn 2")

        var pd = ViewModelProvider(this)[PerangkatDesaViewModel::class.java]
        Log.d("DAri fragmetn 2", "DAri fragmetn 2 nya fragemnmn")
        pd.getPerangkatDesaByNik(nik)
        Log.d("DAri fragmetn 2", nik!!)
        pd.observePerangkatDesaLiveData().observe(
            viewLifecycleOwner
        ){
            nama.text = it[0].namapd.toString()
            Log.d("DAri fragmetn 2", it[0].namapd.toString())
            level.text = it[0].level.toString()
            Log.d("DAri fragmetn 2", it[0].level.toString())
            email.setText(it[0].email.toString())
            Log.d("DAri fragmetn 2", it[0].email.toString())
            password.setText(it[0].password.toString())
            Log.d("DAri fragmetn 2", it[0].password.toString())
        }

        binding.btnFormUpdateProfil.setOnClickListener {
            pd.updateProfilPerangkatDesa(nik!!, email.text.toString(), password.text.toString())

            SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Sukses")
                .setContentText("PROFIL BERHASIL DIPERBAHARUI")
                .setConfirmButton("Iya", {
                    it.dismissWithAnimation()
                })
                .show()
        }
    }
}