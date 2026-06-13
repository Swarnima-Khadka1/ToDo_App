package com.example.to_doapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_doapp.R
import com.example.to_doapp.data.model.Category
import com.example.to_doapp.databinding.FragmentCategoryBinding
import com.example.to_doapp.viewmodel.CategoryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeCategories()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = CategoryAdapter(
            viewModel = viewModel,
            lifecycleOwner = viewLifecycleOwner,
            onCategoryClick = { category ->
                // Navigate to TaskFragment with categoryId
                val action = CategoryFragmentDirections
                    .actionCategoryFragmentToTaskFragment(
                        categoryId = category.id,
                        categoryName = category.name
                    )
                findNavController().navigate(action)
            },
            onCategoryLongClick = { category ->
                showDeleteDialog(category)
            }
        )

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@CategoryFragment.adapter
        }
    }

    private fun observeCategories() {
        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
            binding.tvTaskCount.text = "You have ${categories.size} categories"
        }
    }

    private fun setupFab() {
        binding.fabAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }
    }

    private fun showAddCategoryDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_category, null)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Category")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val nameInput = dialogView.findViewById<TextInputEditText>(R.id.etCategoryName)
                val emojiInput = dialogView.findViewById<TextInputEditText>(R.id.etCategoryEmoji)
                val name = nameInput.text.toString().trim()
                val emoji = emojiInput.text.toString().trim()

                if (name.isNotEmpty()) {
                    viewModel.insertCategory(
                        Category(
                            name = name,
                            iconEmoji = emoji.ifEmpty { "📋" },
                            color = "#FFD700"
                        )
                    )
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteDialog(category: Category) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Category")
            .setMessage("Delete '${category.name}'? All tasks will be deleted too.")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteCategory(category)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}