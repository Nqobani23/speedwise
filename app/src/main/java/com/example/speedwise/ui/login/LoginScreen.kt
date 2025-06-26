package com.example.speedwise.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.ui.home.HomeActivity
import com.example.speedwise.ui.register.RegisterActivity
import com.example.speedwise.ui.login.LoginActivity
import kotlinx.coroutines.*

class LoginScreen : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.IO) {
                val user = db.userDao().login(username, password)
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        startActivity(Intent(this@LoginScreen, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginScreen, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
