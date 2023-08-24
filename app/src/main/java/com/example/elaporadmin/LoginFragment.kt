package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.pegawai.PegawaiDashboardActivity
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginFragment : BottomSheetDialogFragment() {
    private lateinit var txtUsername:TextInputEditText
    private lateinit var txtPassword:TextInputEditText
    private lateinit var btnLogin:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        txtUsername = v.findViewById(R.id.txtUsername)
        txtPassword = v.findViewById(R.id.txtPassword)
        btnLogin = v.findViewById(R.id.btnLogin)


        btnLogin.setOnClickListener {
            if (!txtUsername.text.toString().isEmpty() && !txtPassword.text.toString().isEmpty()){

                var level: String
                var status: Int

                try{
                    lifecycleScope.launch{

                        val response = ApiService.api.login(txtUsername.text.toString(), txtPassword.text.toString())

                        withContext(Dispatchers.Main){

                            if (response.isSuccessful){
                                level = response.body()!!.level
                                status = response.body()!!.status


                                if (status == 500){
                                    SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                        .setContentText("Anda Belum Terdaftar")
                                        .show()
                                }else if(status == 200){
                                    val nama = response.body()!!.nama
                                    val nik = response.body()!!.nik
//                                var bidang_id = response.body()!!.bidang_id

                                    SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                        .setContentText("Berhasil Login")
                                        .setConfirmButton("OKE") {
                                            it.dismissWithAnimation()

                                            when(level){
                                                "admin" ->{
                                                    val intent = Intent(context, DashboardActivity::class.java)
                                                    intent.putExtra("NAMA",nama)
                                                    intent.putExtra("NIK",nik.toString())
//                                                intent.putExtra("BIDANGID",bidang_id.toString())
                                                    startActivity(intent)
                                                }
                                                "pegawai" ->{
                                                    val intent = Intent(context, PegawaiDashboardActivity::class.java)
                                                    intent.putExtra("NAMA",nama)
                                                    intent.putExtra("NIK",nik.toString())
                                                    intent.putExtra("BIDANGID",response.body()!!.bidang_id)
                                                    startActivity(intent)
                                                }
                                                "perangkatdesa" ->{
                                                    val intent = Intent(context, DashboardActivity::class.java)
                                                    intent.putExtra("NAMA", nama)
                                                    intent.putExtra("NIK",nik.toString())
//                                                intent.putExtra("BIDANGID",bidang_id.toString())
                                                    startActivity(intent)
                                                }
                                            }

                                        }
                                        .show()
                                }
                            }
                        }
                    }
                }catch (E:Exception){
                    SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Terjadi Kesalahan. Cek koneksi Anda dan Coba Login Kembali.")
                        .show()
                }

            }else{
                SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Input Tidak Boleh Kosong!!!")
                    .show()
            }
        }

        // Inflate the layout for this fragment
        return v
    }

}