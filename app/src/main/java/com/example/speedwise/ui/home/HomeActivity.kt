package com.example.speedwise.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.ui.budget.MonthlyOverviewActivity
import com.example.speedwise.ui.category.CategoryActivity
import com.example.speedwise.ui.expense.ExpenseActivity
import com.example.speedwise.ui.profile.ProfileActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnCategories = findViewById<Button>(R.id.btnCategories)
        val btnExpenses = findViewById<Button>(R.id.btnExpenses)

        btnCategories.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        btnExpenses.setOnClickListener {
            startActivity(Intent(this, ExpenseActivity::class.java))
        }
        val btnProfile = findViewById<Button>(R.id.btnProfile)
        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        val btnMonthlyOverview = findViewById<Button>(R.id.btnMonthlyOverview)
        btnMonthlyOverview.setOnClickListener {
            startActivity(Intent(this, MonthlyOverviewActivity::class.java))
        }
        val btnCreateExpense  = findViewById<Button>(R.id.btnCreateExpense)
        btnCreateExpense.setOnClickListener {
            startActivity(Intent(this, ExpenseActivity::class.java))
        }

    }
}
