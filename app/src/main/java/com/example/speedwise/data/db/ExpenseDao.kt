package com.example.speedwise.data.db

import androidx.room.*
import com.example.speedwise.data.model.Expense
import com.example.speedwise.data.model.CategoryTotal
import kotlinx.coroutines.flow.Flow // For observable reads

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    // Return Flow for automatic UI updates when data changes
    @Query("SELECT * FROM expense ORDER BY date DESC") // Added default ordering
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getExpensesBetween(start: String, end: String): Flow<List<Expense>>

    @Query("SELECT SUM(amount) FROM expense WHERE date BETWEEN :start AND :end")
    suspend fun getTotalSpentBetween(start: String, end: String): Double? // Return nullable

    @Query("SELECT category, SUM(amount) as total FROM expense WHERE date BETWEEN :start AND :end GROUP BY category")
    suspend fun getTotalSpentByCategory(start: String, end: String): List<CategoryTotal>
}