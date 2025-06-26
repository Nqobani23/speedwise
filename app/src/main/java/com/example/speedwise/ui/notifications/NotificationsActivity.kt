package com.example.speedwise.ui.notifications // Assuming this is your package

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// The binding class name is generated from your XML file name: activity_notificationspop.xml -> ActivityNotificationspopBinding
import com.example.speedwise.databinding.ActivityNotificationspopBinding

class NotificationsActivity : AppCompatActivity() {

    // Declare a lateinit var for the binding object
    private lateinit var binding: ActivityNotificationspopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using the binding object
        binding = ActivityNotificationspopBinding.inflate(layoutInflater)
        // Set the content view to the root of the binding
        setContentView(binding.root)

        binding.switchPopUps.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if (isChecked) "Pop Ups On" else "Pop Ups Off", Toast.LENGTH_SHORT)
                .show()
            // Save preference
        }

        binding.switchLockScreen.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if (isChecked) "Lock Screen Notifications On" else "Lock Screen Notifications Off", Toast.LENGTH_SHORT)
                .show()
            // Save preference
        }

        binding.switchStatusBar.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if (isChecked) "Status Bar Notifications On" else "Status Bar Notifications Off", Toast.LENGTH_SHORT)
                .show()
            // Save preference
        }
    }
}