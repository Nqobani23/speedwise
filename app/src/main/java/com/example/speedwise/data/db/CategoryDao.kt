package com.example.speedwise.data.db

import androidx.room.*
import com.example.speedwise.data.model.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Delete
    suspend fun deleteCategory(category: Category)
}
