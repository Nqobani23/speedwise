package com.example.speedwise.ui.expense

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.data.model.Expense // Assuming this is your Expense model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first // Import for using .first() on a Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilterExpensesActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var btnFilter: Button
    private lateinit var lvFilteredExpenses: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_expenses)

        etStartDate = findViewById(R.id.etStartDate)
        etEndDate = findViewById(R.id.etEndDate)
        btnFilter = findViewById(R.id.btnFilter)
        lvFilteredExpenses = findViewById(R.id.lvFilteredExpenses)

        btnFilter.setOnClickListener {
            val startDateString = etStartDate.text.toString()
            val endDateString = etEndDate.text.toString()

            if (startDateString.isBlank() || endDateString.isBlank()) {
                Toast.makeText(this, "Please enter both start and end dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Use lifecycleScope for coroutines tied to the Activity's lifecycle
            lifecycleScope.launch {
                val filteredExpenses: List<Expense> = fetchFilteredExpenses(startDateString, endDateString)

                // The 'map' function should work correctly here if 'filteredExpenses' is a List
                val displayList: List<String> = filteredExpenses.map { expense ->
                    // Customize this string based on your Expense entity properties
                    "Title: ${expense.title} | Amount: R${expense.amount} | Date: ${expense.date}"
                }

                updateListView(displayList)
            }
        }
    }

    // Suspend function to fetch data from the database
    private suspend fun fetchFilteredExpenses(startDate: String, endDate: String): List<Expense> {
        return withContext(Dispatchers.IO) {
            // Your DAO returns Flow<List<Expense>>, so we use .first() to get the
            // first emission from the Flow, which will be the List<Expense>.
            db.expenseDao().getExpensesBetween(startDate, endDate).first()
        }
    }

    // Function to update the ListView on the Main thread
    private suspend fun updateListView(data: List<String>) {
        withContext(Dispatchers.Main) {
            val adapter = ArrayAdapter(this@FilterExpensesActivity, android.R.layout.simple_list_item_1, data)
            lvFilteredExpenses.adapter = adapter
            if (data.isEmpty()) {
                Toast.makeText(this@FilterExpensesActivity, "No expenses found for the selected period.", Toast.LENGTH_LONG).show()
            }
        }
    }
}