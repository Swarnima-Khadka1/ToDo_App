package com.example.to_doapp.ui.task

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doapp.data.model.Task
import com.example.to_doapp.databinding.ItemTaskBinding

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onTaskLongClick: (Task) -> Unit,
    private val onTaskChecked: (Task, Boolean) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {

            // Set title
            binding.tvTaskTitle.text = task.title

            // Set description
            if (task.description.isNotEmpty()) {
                binding.tvTaskDescription.text = task.description
                binding.tvTaskDescription.visibility = android.view.View.VISIBLE
            } else {
                binding.tvTaskDescription.visibility = android.view.View.GONE
            }

            // Set due date
            if (task.dueDate.isNotEmpty()) {
                binding.tvDueDate.text = "Due: ${task.dueDate}"
            } else {
                binding.tvDueDate.text = ""
            }

            // Set priority color
            when (task.priority) {
                "High" -> binding.tvPriority.setBackgroundColor(
                    android.graphics.Color.parseColor("#FF5733")
                )
                "Medium" -> binding.tvPriority.setBackgroundColor(
                    android.graphics.Color.parseColor("#FFA500")
                )
                "Low" -> binding.tvPriority.setBackgroundColor(
                    android.graphics.Color.parseColor("#4CAF50")
                )
            }
            binding.tvPriority.text = task.priority

            // Set checkbox without triggering listener
            binding.cbTaskComplete.setOnCheckedChangeListener(null)
            binding.cbTaskComplete.isChecked = task.isCompleted

            // Strikethrough if completed
            if (task.isCompleted) {
                binding.tvTaskTitle.paintFlags =
                    binding.tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTaskTitle.alpha = 0.5f
            } else {
                binding.tvTaskTitle.paintFlags =
                    binding.tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvTaskTitle.alpha = 1.0f
            }

            // Checkbox listener
            binding.cbTaskComplete.setOnCheckedChangeListener { _, isChecked ->
                onTaskChecked(task, isChecked)
            }

            // Click → edit task
            binding.root.setOnClickListener {
                onTaskClick(task)
            }

            // Long click → delete task
            binding.root.setOnLongClickListener {
                onTaskLongClick(task)
                true
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}