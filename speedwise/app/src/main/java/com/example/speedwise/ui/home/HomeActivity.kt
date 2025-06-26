package com.example.speedwise.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.ui.budget.MonthlyOverviewActivity
import com.example.speedwise.ui.category.CategoryActivity
import com.example.speedwise.ui.expense.ExpenseActivity
import com.example.speedwise.ui.notifications.NotificationActivity
import com.example.speedwise.ui.profile.ProfileActivity
import com.example.speedwise.ui.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnCategories = findViewById<Button>(R.id.btnCategories)
        val btnExpenses = findViewById<Button>(R.id.btnExpenses)
        val btnNotifications = findViewById<Button>(R.id.btnNotifications)

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
       /* val btnNotificationActivity = findViewById<Button>(btnNotifications)*/
        btnNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }


    }
}
