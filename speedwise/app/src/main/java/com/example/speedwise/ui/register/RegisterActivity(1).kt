package com.example.speedwise.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.data.model.User
import com.example.speedwise.ui.login.LoginActivity
import kotlinx.coroutines.*
import androidx.lifecycle.lifecycleScope
/*import com.example.speedwise.R.id.btnLoginRedirect*/

class RegisterActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNewUsername = findViewById<EditText>(R.id.etNewUsername)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val etNewEmail = findViewById<EditText>(R.id.etNewEmail)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)
/*        val btnLoginRedirect = findViewById<Button>(btnLoginRedirect)*/

        btnCreateAccount.setOnClickListener {
            val username = etNewUsername.text.toString()
            val password = etNewPassword.text.toString()
            val email = etNewEmail.text.toString()

            if (username.isBlank() || password.isBlank() || email.isBlank()) {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Use lifecycleScope instead of GlobalScope
            lifecycleScope.launch(Dispatchers.IO) {
                val existing = db.userDao().getUserByUsername(username)
                withContext(Dispatchers.Main) {
                    if (existing != null) {
                        Toast.makeText(this@RegisterActivity, "Username already taken", Toast.LENGTH_SHORT).show()
                    } else {
                        db.userDao().insertUser(User(username = username, password = password, email = email))
                        Toast.makeText(this@RegisterActivity, "Account created", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
