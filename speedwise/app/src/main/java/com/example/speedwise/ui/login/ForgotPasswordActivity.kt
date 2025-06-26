package com.example.speedwise.ui.login

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.R.*


class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_forgot_password)

        val btnResetPassword = findViewById<Button>(R.id.btnResetPassword)
        btnResetPassword.setOnClickListener {

            Toast.makeText(this, "Reset link sent to your email.", Toast.LENGTH_LONG).show()
            finish()
        }



    }
}

