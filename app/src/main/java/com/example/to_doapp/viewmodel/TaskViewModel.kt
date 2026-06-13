package com.example.to_doapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.to_doapp.data.database.AppDatabase
import com.example.to_doapp.data.model.Task
import com.example.to_doapp.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = TaskRepository(
            database.categoryDao(),
            database.taskDao()
        )
    }

    fun getTasksByCategory(categoryId: Int): LiveData<List<Task>> {
        return repository.getTasksByCategory(categoryId)
    }

    fun getPendingTasks(): LiveData<List<Task>> {
        return repository.getPendingTasks()
    }

    fun searchTasks(query: String): LiveData<List<Task>> {
        return repository.searchTasks(query)
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun updateTaskCompletion(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTaskCompletion(taskId, isCompleted)
        }
    }
}