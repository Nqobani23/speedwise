package com.example.speedwise.ui.profile

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        /*
                val btnResetPassword = findViewById<Button>(R.id.btnResetPassword)*/
        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)

        /*btnResetPassword.setOnClickListener {
            Toast.makeText(this, "Reset link sent to your email.", Toast.LENGTH_LONG).show()
            finish()
        }*/

        btnChangePassword.setOnClickListener {
            Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}