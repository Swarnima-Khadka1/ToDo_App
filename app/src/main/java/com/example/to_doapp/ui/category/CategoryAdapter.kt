package com.example.to_doapp.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doapp.data.model.Category
import com.example.to_doapp.databinding.ItemCategoryBinding
import com.example.to_doapp.viewmodel.CategoryViewModel
import androidx.lifecycle.LifecycleOwner


class CategoryAdapter(
    private val viewModel: CategoryViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val onCategoryClick: (Category) -> Unit,
    private val onCategoryLongClick: (Category) -> Unit
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.name
            binding.tvCategoryIcon.text = category.iconEmoji

            viewModel.getTaskCountForCategory(category.id)
                .observe(lifecycleOwner) { count ->
                    binding.tvCategoryTaskCount.text = "$count Tasks"
                }
            // Single click → open tasks
            binding.root.setOnClickListener {
                onCategoryClick(category)
            }

            // Long click → delete/edit options
            binding.root.setOnLongClickListener {
                onCategoryLongClick(category)
                true
            }
        }
    }

    // DiffCallback tells RecyclerView what changed
    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}