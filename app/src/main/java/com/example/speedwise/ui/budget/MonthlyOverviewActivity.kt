package com.example.speedwise.ui.budget

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.R
import com.example.speedwise.data.db.AppDatabase
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MonthlyOverviewActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }
    private val categoryGoals = mapOf(
        "Groceries" to 2000.0,
        "Transport" to 1500.0,
        "Entertainment" to 1000.0,
        "Other" to 800.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_overview)

        val layout = findViewById<LinearLayout>(R.id.layoutCategoryProgress)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startOfMonth = sdf.format(Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 1) }.time)
        val today = sdf.format(Date())

        GlobalScope.launch(Dispatchers.IO) {
            val totals = db.expenseDao().getTotalSpentByCategory(startOfMonth, today)
            withContext(Dispatchers.Main) {
                totals.forEach {
                    val categoryName = it.category
                    val spent = it.total
                    val goal = categoryGoals[categoryName] ?: 1000.0
                    val percent = (spent / goal * 100).coerceAtMost(100.0).toInt()

                    val title = TextView(this@MonthlyOverviewActivity).apply {
                        text = "$categoryName - R$spent of R$goal"
                        setTextColor(android.graphics.Color.WHITE)
                    }

                    val progress = ProgressBar(this@MonthlyOverviewActivity, null, android.R.attr.progressBarStyleHorizontal).apply {
                        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40)
                        max = 100
                        progress = percent
                    }

                    layout.addView(title)
                    layout.addView(progress)
                }
            }
        }
    }
}
