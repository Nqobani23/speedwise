package com.example.speedwise.ui.support

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R

class SupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        findViewById<android.widget.Button>(R.id.btnSendSupportEmail).setOnClickListener {
            Toast.makeText(this, "Support request sent. We'll contact you soon.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
