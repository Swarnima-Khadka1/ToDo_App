package com.example.to_doapp

import android.app.Application
import com.example.to_doapp.data.database.AppDatabase
import com.example.to_doapp.data.model.Category
import com.example.to_doapp.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskFlowApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        insertDefaultCategories()
    }

    private fun insertDefaultCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(this@TaskFlowApplication)
            val repository = TaskRepository(
                database.categoryDao(),
                database.taskDao()
            )
            val count = database.categoryDao().getCategoryCount()
            if (count == 0) {
                val defaultCategories = listOf(
                    Category(name = "Today", iconEmoji = "☀️", color = "#FFD700"),
                    Category(name = "Personal", iconEmoji = "👤", color = "#FF5733"),
                    Category(name = "Work", iconEmoji = "💼", color = "#3498DB"),
                    Category(name = "Shopping", iconEmoji = "🛒", color = "#2ECC71"),
                    Category(name = "Study", iconEmoji = "📚", color = "#9B59B6")
                )

                defaultCategories.forEach { category ->
                    repository.insertCategory(category)
                }
            }
        }
    }
}