package com.example.to_doapp.data.model

import com.example.to_doapp.data.model.Category

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = 5   // 5 = CASCADE constant value
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val categoryId: Int,
    val priority: String = "Medium",
    val dueDate: String = "",
    val isCompleted: Boolean = false
)