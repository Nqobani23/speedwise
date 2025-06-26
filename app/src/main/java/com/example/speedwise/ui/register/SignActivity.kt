package com.example.speedwise.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.data.model.User
import com.example.speedwise.ui.login.LoginActivity
import kotlinx.coroutines.*

class SignupActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etSignupEmail)
        val etPassword = findViewById<EditText>(R.id.etSignupPassword)
        val btnCreate = findViewById<Button>(R.id.btnCreateAccount)

        btnCreate.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(name= name, email = email, password = password)

            GlobalScope.launch(Dispatchers.IO) {
                db.userDao().insertUser(user)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignupActivity, "Account created!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}
