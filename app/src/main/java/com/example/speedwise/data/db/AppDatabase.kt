package com.example.speedwise.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.speedwise.data.model.Category // Assuming this exists
import com.example.speedwise.data.model.Expense // Assuming this exists
import com.example.speedwise.data.model.User    // Assuming this exists

@Database(entities = [User::class, Category::class, Expense::class], version = 1, exportSchema = false) // Added exportSchema = false
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "spendwise_db"
                )
                    // It's good practice to handle migrations in production apps.
                    // For development, you can use fallbackToDestructiveMigration().
                    // .fallbackToDestructiveMigration() // Use with caution!
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}