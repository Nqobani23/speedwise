package com.example.speedwise.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val btnSignOut = findViewById<Button>(R.id.btnSignOut)
        // Declare and initialize btnShare
        val btnShare = findViewById<Button>(R.id.btnShare)
        // You also have a "Change Password" button, if you want to use it:
        // val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)

        tvEmail.text = "student@email.com" // Optional: Load from SharedPreferences or DB

        btnSignOut.setOnClickListener {
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            // The btnShare.setOnClickListener should NOT be here
        }

        // Set the OnClickListener for btnShare independently
        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "SpendWise App") // You can customize this subject
                putExtra(Intent.EXTRA_TEXT, "Hey! Check out SpendWise. It's a great app to track your budget smartly. You can download it from: [Your App Link Here]") // Customize your message
            }
            startActivity(Intent.createChooser(intent, "Share with"))
        }

        // If you want to add functionality to the "Change Password" button:
        // btnChangePassword.setOnClickListener {
        //     // Handle change password action
        //     Toast.makeText(this, "Change Password Clicked", Toast.LENGTH_SHORT).show()
        //     // For example, navigate to a ChangePasswordActivity
        //     // startActivity(Intent(this, ChangePasswordActivity::class.java))
        // }
    }
}