package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.elaporadmin.databinding.ActivityMainBinding
import com.example.elaporadmin.pegawai.PegawaiDashboardActivity
import com.example.elaporadmin.perangkatdesa.PerangkatDesaDashboardActivity
import com.example.elaporadmin.retrofit.ApiService
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var btnLogin:Button
    private lateinit var txtUsername: TextInputEditText
    private lateinit var txtPassword: TextInputEditText
    private lateinit var progressBar:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        btnLogin = binding.btnLogin
        txtUsername = binding.txtUsername
        txtPassword = binding.txtPassword
        progressBar = binding.progressBar

        showLoading(false)

        btnLogin.setOnClickListener {
            if (txtUsername.text.toString().isNotEmpty() && txtPassword.text.toString().isNotEmpty()){

                var level: String
                var status: Int

                showLoading(true)

                try{
                    GlobalScope.launch(Dispatchers.IO){

                        val response = ApiService.api.login(txtUsername.text.toString(), txtPassword.text.toString())

                        withContext(Dispatchers.Main){

                            if (response.isSuccessful){
                                level = response.body()!!.level
                                status = response.body()!!.status


                                if (status == 500){
                                    showLoading(false)
                                    SweetAlertDialog(this@MainActivity, SweetAlertDialog.WARNING_TYPE)
                                        .setContentText("Anda Belum Terdaftar")
                                        .show()
                                }else if(status == 200){
                                    val nama = response.body()!!.nama
                                    val nik = response.body()!!.nik
                                    val bidang_id = response.body()!!.bidang_id
                                    val kecamatan_id = response.body()!!.kecamatan_id
//                                var bidang_id = response.body()!!.bidang_id

                                    showLoading(false)
                                    SweetAlertDialog(this@MainActivity, SweetAlertDialog.SUCCESS_TYPE)
                                        .setContentText("Berhasil Login")
                                        .setConfirmButton("OKE") {
                                            it.dismissWithAnimation()

                                            when(level){
                                                "admin" ->{
                                                    val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                                                    intent.putExtra("NAMA",nama)
                                                    intent.putExtra("NIK",nik.toString())
                                                    intent.putExtra("BIDANGID",bidang_id)
                                                    intent.putExtra("KECAMATANID",kecamatan_id)
                                                    startActivity(intent)
                                                }
                                                "pegawai" ->{
                                                    val intent = Intent(this@MainActivity, PegawaiDashboardActivity::class.java)
                                                    intent.putExtra("NAMA",nama)
                                                    intent.putExtra("NIK",nik.toString())
                                                    intent.putExtra("BIDANGID",bidang_id)
                                                    intent.putExtra("KECAMATANID",kecamatan_id)
                                                    startActivity(intent)
                                                }
                                                "perangkatdesa" ->{
                                                    val intent = Intent(this@MainActivity, PerangkatDesaDashboardActivity::class.java)
                                                    intent.putExtra("NAMA", nama)
                                                    intent.putExtra("NIK",nik.toString())
                                                    intent.putExtra("BIDANGID",bidang_id)
                                                    intent.putExtra("KECAMATANID",kecamatan_id)
                                                    startActivity(intent)
                                                }
                                            }

                                        }
                                        .show()
                                }


                            }
                        }
                    }
                }catch (E: Exception){
                    SweetAlertDialog(this@MainActivity, SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Terjadi Kesalahan. Cek koneksi Anda dan Coba Login Kembali.")
                        .show()
                }

            }else{
                SweetAlertDialog(this@MainActivity, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Input Tidak Boleh Kosong!!!")
                    .show()
            }
        }



//        btnLogin.setOnClickListener {
//            val loginFragment = LoginFragment()
//            loginFragment.show(supportFragmentManager, loginFragment.tag)
//        }
    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

}