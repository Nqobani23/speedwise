package com.example.speedwise.ui.chart // Assuming this is the correct package

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.databinding.ActivityChartBinding // Assuming ViewBinding
// import com.github.mikephil.charting.charts.PieChart // Not directly used as a type here
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate // For better color options
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlin.text.clear
import kotlin.text.toList

class ChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChartBinding // Make sure you have viewBinding enabled in build.gradle
    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadCategoryData()
    }

    private fun loadCategoryData() {
        lifecycleScope.launch { // Launch on Main dispatcher by default
            val entries = withContext(Dispatchers.IO) { // Switch to IO for DB
                // Ensure getTotalSpentByCategory is suspend or Flow in DAO
                val data = db.expenseDao().getTotalSpentByCategory("2023-01-01", "2025-12-31") // Static date range
                data.map { PieEntry(it.total.toFloat(), it.category) }
            }

            // Back on the Main dispatcher
            if (entries.isNotEmpty()) {
                val dataSet = PieDataSet(entries, "Spending by Category")
                dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList() // Use predefined colors
                dataSet.valueTextColor = Color.BLACK
                dataSet.valueTextSize = 12f

                val pieData = PieData(dataSet)
                binding.pieChart.data = pieData
                binding.pieChart.description.isEnabled = false // Optional: Disable description
                binding.pieChart.setEntryLabelColor(Color.BLACK) // Optional: Make entry labels visible
                binding.pieChart.animateY(1000) // Optional: Add animation
                binding.pieChart.invalidate() // Redraw
            } else {
                // Handle case where there's no data to display (e.g., show a message)
                binding.pieChart.clear() // Clear any old data
                binding.pieChart.invalidate()
                // Optionally show a TextView with "No data available"
            }
        }
    }
}