package com.example.speedwise.ui.expense

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.speedwise.data.db.AppDatabase
import com.example.speedwise.data.model.Category
import com.example.speedwise.data.model.Expense
import com.example.speedwise.databinding.ActivityExpenseBinding
import kotlinx.coroutines.*

class ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseBinding
    private val db by lazy { AppDatabase.getDatabase(this) }
    private var photoPath: String? = null

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Register the image picker launcher
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                photoPath = imageUri.toString()
                binding.ivPreview.setImageURI(imageUri)
            }
        }

        loadCategories()

        binding.btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        binding.btnSaveExpense.setOnClickListener {
            val title = binding.etExpenseTitle.text.toString()
            val desc = binding.etExpenseDesc.text.toString()
            val category = binding.spinnerCategory.selectedItem.toString()
            val date = binding.etExpenseDate.text.toString()
            val start = binding.etStartTime.text.toString()
            val end = binding.etEndTime.text.toString()
            val amountStr = binding.etAmount.text.toString()

            if (title.isEmpty() || date.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expense = Expense(
                title = title,
                description = desc,
                category = category,
                date = date,
                startTime = start,
                endTime = end,
                amount = amountStr.toDoubleOrNull() ?: 0.0,
                imagePath = photoPath
            )

            GlobalScope.launch(Dispatchers.IO) {
                db.expenseDao().insertExpense(expense)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ExpenseActivity, "Expense saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadCategories() {
        GlobalScope.launch(Dispatchers.IO) {
            val categories = db.categoryDao().getAllCategories()
            val names = categories.map(Category::name)
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(this@ExpenseActivity, android.R.layout.simple_spinner_item, names)
                binding.spinnerCategory.adapter = adapter
            }
        }
    }
}
