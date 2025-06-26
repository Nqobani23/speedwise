package com.example.speedwise.ui.category

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.data.model.Category
import com.example.speedwise.databinding.ActivityCategoryBinding
import kotlinx.coroutines.*
import com.example.speedwise.data.model.CategoryTotal

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private val db by lazy { AppDatabase.getDatabase(this) }
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadCategories()

        binding.btnAddCategory.setOnClickListener {
            val name = binding.etCategoryName.text.toString()
            val desc = binding.etCategoryDesc.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            category = Category(name = name, description = desc)

            GlobalScope.launch(Dispatchers.IO) {
                db.categoryDao().insertCategory(category)
                loadCategories()
            }
        }
    }

    private fun loadCategories() {
        GlobalScope.launch(Dispatchers.IO) {
            val categories = db.categoryDao().getAllCategories()
            val names = categories.map { it.name }
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(this@CategoryActivity, android.R.layout.simple_list_item_1, names)
                binding.lvCategories.adapter = adapter
            }
        }
    }
}
