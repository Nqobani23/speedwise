package com.example.speedwise.ui.budget

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class BudgetStatusActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_status)

        val etMin = findViewById<EditText>(R.id.etMinBudget)
        val etMax = findViewById<EditText>(R.id.etMaxBudget)
        val btnCheck = findViewById<Button>(R.id.btnCheckBudget)
        val tvResult = findViewById<TextView>(R.id.tvBudgetResult)

        btnCheck.setOnClickListener {
            val min = etMin.text.toString().toDoubleOrNull()
            val max = etMax.text.toString().toDoubleOrNull()

            if (min == null || max == null) {
                Toast.makeText(this, "Enter both budget values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val monthStart = SimpleDateFormat("yyyy-MM-01", Locale.getDefault()).format(Date())
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            lifecycleScope.launch(Dispatchers.IO) {
                val total = db.expenseDao().getTotalSpentBetween(monthStart, today)
                val status = when {
                    total < min -> "You're under budget ✅"
                    total > max -> "You're over budget ❌"
                    else -> "You're within your budget 🎯"
                }

                withContext(Dispatchers.Main) {
                    val message = getString(R.string.budget_status_text, total, status)
                    tvResult.text = message
                }
            }

        }
    }
}
