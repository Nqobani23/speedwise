package com.example.speedwise.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvEmail: TextView
    private lateinit var btnSignOut: Button
    private lateinit var btnChangePassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvEmail = findViewById(R.id.tvEmail) // Make sure your XML layout has this TextView
        btnChangePassword = findViewById(R.id.btnChangePassword)
        btnSignOut = findViewById(R.id.btnSignOut)

        // 🔐 Get the current user from Firebase
        val currentUser = FirebaseAuth.getInstance().currentUser
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val email = sharedPref.getString("email", "Not signed in")
        tvEmail.text = email


        // 📧 Display the user's email
        if (currentUser != null) {
            tvEmail.text = currentUser.email
        } else {
            tvEmail.text = "Not signed in"
        }

        btnChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
