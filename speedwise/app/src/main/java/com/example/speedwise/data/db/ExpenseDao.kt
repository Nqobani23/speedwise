package com.example.speedwise.data.db

import androidx.room.*
import com.example.speedwise.data.model.Expense
import com.example.speedwise.data.model.CategoryTotal

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expense")
    fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM expense WHERE date BETWEEN :start AND :end")
    fun getExpensesBetween(start: String, end: String): List<Expense>

    @Query("SELECT SUM(amount) FROM expense WHERE date BETWEEN :start AND :end")
    fun getTotalSpentBetween(start: String, end: String): Double

    @Query("SELECT category, SUM(amount) as total FROM expense WHERE date BETWEEN :start AND :end GROUP BY category")
    fun getTotalSpentByCategory(start: String, end: String): List<CategoryTotal>



}
