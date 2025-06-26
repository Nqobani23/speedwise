package com.example.speedwise.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.speedwise.data.model.Category
import com.example.speedwise.data.model.User
import com.example.speedwise.data.model.Expense


@Database(entities = [User::class, Expense::class, Category::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "speedwise_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

