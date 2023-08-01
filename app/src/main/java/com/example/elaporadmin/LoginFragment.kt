package com.example.elaporadmin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

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
                val intent = Intent(
                    context,
                    DashboardActivity::class.java
                )

                startActivity(intent)
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