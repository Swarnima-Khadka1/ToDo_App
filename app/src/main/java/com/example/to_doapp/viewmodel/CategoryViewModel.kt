package com.example.to_doapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.to_doapp.data.database.AppDatabase
import com.example.to_doapp.data.model.Category
import com.example.to_doapp.repository.TaskRepository
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allCategories: LiveData<List<Category>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = TaskRepository(
            database.categoryDao(),
            database.taskDao()
        )
        allCategories = repository.allCategories
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            repository.insertCategory(category)
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            repository.updateCategory(category)
        }
    }
    fun getTaskCountForCategory(categoryId: Int): LiveData<Int> {
        return repository.getTaskCountForCategory(categoryId)
    }
    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }
}