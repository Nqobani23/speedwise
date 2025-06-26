package com.example.speedwise.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.data.model.User
import com.example.speedwise.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNewEmail = findViewById<EditText>(R.id.etNewEmail)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)

        btnCreateAccount.setOnClickListener {
            val email = etNewEmail.text.toString().trim()
            val password = etNewPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val existingUser = withContext(Dispatchers.IO) {
                    db.userDao().getUserByEmail(email)
                }

                if (existingUser != null) {
                    Toast.makeText(this@RegisterActivity, "Email already registered", Toast.LENGTH_SHORT).show()
                } else {
                    withContext(Dispatchers.IO) {
                        db.userDao().insertUser(
                            User(
                                name = "Default Name", // You can let user enter name later
                                email = email,
                                password = password
                            )
                        )
                    }
                    Toast.makeText(this@RegisterActivity, "Account created successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}
