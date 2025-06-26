package com.example.speedwise.ui.expense

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import kotlinx.coroutines.*

class FilterExpensesActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_expenses)

        val etStartDate = findViewById<EditText>(R.id.etStartDate)
        val etEndDate = findViewById<EditText>(R.id.etEndDate)
        val btnFilter = findViewById<Button>(R.id.btnFilter)
        val lvFilteredExpenses = findViewById<ListView>(R.id.lvFilteredExpenses)

        btnFilter.setOnClickListener {
            val start = etStartDate.text.toString()
            val end = etEndDate.text.toString()

            if (start.isBlank() || end.isBlank()) {
                Toast.makeText(this, "Enter both dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.IO) {
                val filtered = db.expenseDao().getExpensesBetween(start, end)
                val list = filtered.map {
                    "${it.title} | R${it.amount} | ${it.date}"
                }

                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(this@FilterExpensesActivity, android.R.layout.simple_list_item_1, list)
                    lvFilteredExpenses.adapter = adapter
                }
            }
        }
    }
}
