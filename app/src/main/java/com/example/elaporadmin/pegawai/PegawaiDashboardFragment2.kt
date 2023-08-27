package com.example.elaporadmin.pegawai

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.R
import com.example.elaporadmin.databinding.FragmentPegawaiDashboard2Binding
import com.example.elaporadmin.databinding.FragmentPerangkatDesa2Binding
import com.example.elaporadmin.perangkatdesa.PerangkatDesaViewModel

class PegawaiDashboardFragment2 : Fragment() {

    private lateinit var binding: FragmentPegawaiDashboard2Binding
    private lateinit var nama: TextView
    private lateinit var level: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPegawaiDashboard2Binding.inflate(layoutInflater, container, false)
        initLayout()

        return binding.root
    }

    private fun initLayout() {
        val nik = requireArguments().getString("NIK")
        nama = binding.namaPd
        level = binding.levelPd
        email = binding.frmEmailPd
        password = binding.frmPasswordPd

        var pegawai = ViewModelProvider(this)[PegawaiViewModel::class.java]
        pegawai.getPegawaiByNik(nik)
        pegawai.observePegawaiLiveData().observe(
            viewLifecycleOwner
        ){
            nama.text = it[0].namapegawai.toString()
            level.text = it[0].level.toString()
            email.setText(it[0].email.toString())
            password.setText(it[0].password.toString())

        }

        binding.btnFormUpdateProfilPd.setOnClickListener {
            pegawai.updateProfilPegawai(nik!!, email.text.toString(), password.text.toString())

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