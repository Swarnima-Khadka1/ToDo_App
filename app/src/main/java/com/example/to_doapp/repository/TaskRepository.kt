package com.example.to_doapp.repository

import androidx.lifecycle.LiveData
import com.example.to_doapp.data.dao.CategoryDao
import com.example.to_doapp.data.dao.TaskDao
import com.example.to_doapp.data.model.Category
import com.example.to_doapp.data.model.Task

class TaskRepository (
    private val categoryDao: CategoryDao,
    private val taskDao: TaskDao
) {
    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }
    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    fun getTasksByCategory(categoryId: Int): LiveData<List<Task>> {
        return taskDao.getTasksByCategory(categoryId)
    }
    fun getTaskCountForCategory(categoryId: Int): LiveData<Int> {
        return categoryDao.getTaskCountForCategory(categoryId)
    }
    fun getPendingTasks(): LiveData<List<Task>> {
        return taskDao.getPendingTasks()
    }

    fun searchTasks(query: String): LiveData<List<Task>> {
        return taskDao.searchTasks(query)
    }

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean) {
        taskDao.updateTaskCompletion(taskId, isCompleted)
    }
}
