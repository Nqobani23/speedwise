package com.example.speedwise.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.ui.login.LoginActivity
import com.example.speedwise.ui.profile.ProfileActivity
import com.example.speedwise.ui.support.SupportActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val btnAccount = findViewById<Button>(R.id.btnAccount)
        val btnNotifications = findViewById<Button>(R.id.btnNotifications)
        val btnSupport = findViewById<Button>(R.id.btnSupport)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnAccount.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

       /* btnNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }*/

        btnSupport.setOnClickListener {
            startActivity(Intent(this, SupportActivity::class.java))
        }

        btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
