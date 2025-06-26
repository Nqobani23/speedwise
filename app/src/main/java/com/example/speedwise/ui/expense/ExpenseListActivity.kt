package com.example.speedwise.ui.expense

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Import lifecycleScope
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.data.model.Expense // Make sure Expense model is imported
import kotlinx.coroutines.flow.collectLatest // For collecting Flow
import kotlinx.coroutines.launch

class ExpenseListActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }
    private lateinit var expensesAdapter: ArrayAdapter<String> // Declare adapter at class level
    private val displayedExpenses = mutableListOf<String>() // Data source for the adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        val listView = findViewById<ListView>(R.id.lvExpenses)
        setupAdapter(listView)
        observeExpenses()
    }

    private fun setupAdapter(listView: ListView) {
        expensesAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            displayedExpenses
        )
        listView.adapter = expensesAdapter
    }

    private fun observeExpenses() {
        // Use lifecycleScope for coroutines tied to the Activity's lifecycle
        lifecycleScope.launch {
            db.expenseDao().getAllExpenses().collectLatest { expensesList ->
                // This block will be re-executed whenever the expenses in the DB change
                displayedExpenses.clear()
                val expenseStrings = expensesList.map { expense ->
                    // Assuming Expense model has: title, amount, date, category
                    // Adjust if your Expense model has different field names
                    "${expense.title} | R${expense.amount} | ${expense.date} | ${expense.category}"
                }
                displayedExpenses.addAll(expenseStrings)
                expensesAdapter.notifyDataSetChanged() // Update the ListView
            }
        }
    }
}