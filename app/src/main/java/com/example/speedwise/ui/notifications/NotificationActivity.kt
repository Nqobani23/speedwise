package com.example.speedwise.ui.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.speedwise.R

class NotificationActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val popUpSwitch = findViewById<SwitchCompat>(R.id.switchPopUps)
        val lockSwitch = findViewById<SwitchCompat>(R.id.switchLockScreen)
        val statusBarSwitch = findViewById<SwitchCompat>(R.id.switchStatusBar)

        popUpSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Pop Ups: ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }

        lockSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Lock Screen: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
        }

        statusBarSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Status Bar: ${if (isChecked) "Shown" else "Hidden"}", Toast.LENGTH_SHORT).show()
        }
    }
}
