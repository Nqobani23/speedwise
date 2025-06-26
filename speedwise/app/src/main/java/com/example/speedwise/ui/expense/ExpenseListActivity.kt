package com.example.speedwise.ui.expense

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import kotlinx.coroutines.*

class ExpenseListActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        val listView = findViewById<ListView>(R.id.lvExpenses)

        GlobalScope.launch(Dispatchers.IO) {
            val expenses = db.expenseDao().getAllExpenses()
            val expenseStrings = expenses.map { expense ->
                "${expense.title} | R${expense.amount} | ${expense.date} | ${expense.category}"
            }

            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    this@ExpenseListActivity,
                    android.R.layout.simple_list_item_1,
                    expenseStrings
                )
                listView.adapter = adapter
            }
        }
    }
}
