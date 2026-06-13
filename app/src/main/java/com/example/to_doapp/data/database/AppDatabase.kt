package com.example.to_doapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_doapp.data.dao.CategoryDao
import com.example.to_doapp.data.dao.TaskDao
import com.example.to_doapp.data.model.Category
import com.example.to_doapp.data.model.Task

@Database(
    entities = [Category::class, Task::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "to_doapp_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}