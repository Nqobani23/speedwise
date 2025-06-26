package com.example.speedwise.ui.category

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import kotlinx.coroutines.*

class CategoryTotalsActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_totals)

        val etStartDate = findViewById<EditText>(R.id.etStartDate)
        val etEndDate = findViewById<EditText>(R.id.etEndDate)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val lvCategoryTotals = findViewById<ListView>(R.id.lvCategoryTotals)

        btnCalculate.setOnClickListener {
            val start = etStartDate.text.toString()
            val end = etEndDate.text.toString()

            if (start.isBlank() || end.isBlank()) {
                Toast.makeText(this, "Enter both dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.IO) {
                val results = db.expenseDao().getTotalSpentByCategory(start, end)
                val lines = results.map { "${it.category}: R${it.total}" }

                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(this@CategoryTotalsActivity, android.R.layout.simple_list_item_1, lines)
                    lvCategoryTotals.adapter = adapter
                }
            }
        }
    }
}
