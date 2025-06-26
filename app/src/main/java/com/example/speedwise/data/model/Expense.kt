package com.example.speedwise.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")  // ✅ singular!
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val amount: Double,
    val imagePath: String? = null
)
