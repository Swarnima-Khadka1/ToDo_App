package com.example.to_doapp.ui.task

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_doapp.data.model.Task
import com.example.to_doapp.databinding.FragmentTaskBinding
import com.example.to_doapp.viewmodel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TaskViewModel by viewModels()
    private val args: TaskFragmentArgs by navArgs()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set category name in header
        binding.tvCategoryName.text = args.categoryName

        setupRecyclerView()
        observeTasks()
        setupFab()
        setupSearch()
        setupBackButton()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            onTaskClick = { task ->
                showEditTaskDialog(task)
            },
            onTaskLongClick = { task ->
                showDeleteTaskDialog(task)
            },
            onTaskChecked = { task, isChecked ->
                viewModel.updateTaskCompletion(task.id, isChecked)
            }
        )

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@TaskFragment.adapter
        }
    }

    private fun observeTasks() {
        viewModel.getTasksByCategory(args.categoryId)
            .observe(viewLifecycleOwner) { tasks ->
                adapter.submitList(tasks)
                binding.tvTaskCount.text = "${tasks.size} Tasks"
            }
    }

    private fun setupFab() {
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.isEmpty()) {
                    observeTasks()
                } else {
                    viewModel.searchTasks(query)
                        .observe(viewLifecycleOwner) { tasks ->
                            adapter.submitList(tasks)
                        }
                }
            }
        })
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(com.example.to_doapp.R.layout.dialog_add_task, null)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val titleInput = dialogView
                    .findViewById<TextInputEditText>(com.example.to_doapp.R.id.etTaskTitle)
                val descInput = dialogView
                    .findViewById<TextInputEditText>(com.example.to_doapp.R.id.etTaskDescription)
                val dueDateInput = dialogView
                    .findViewById<TextInputEditText>(com.example.to_doapp.R.id.etDueDate)
                val priorityInput = dialogView
                    .findViewById<TextInputEditText>(com.example.to_doapp.R.id.etPriority)

                val title = titleInput.text.toString().trim()
                val desc = descInput.text.toString().trim()
                val dueDate = dueDateInput.text.toString().trim()
                val priority = priorityInput.text.toString().trim()

                if (title.isNotEmpty()) {
                    viewModel.insertTask(
                        Task(
                            title = title,
                            description = desc,
                            categoryId = args.categoryId,
                            priority = priority.ifEmpty { "Medium" },
                            dueDate = dueDate
                        )
                    )
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditTaskDialog(task: Task) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(com.example.to_doapp.R.layout.dialog_add_task, null)

        // Pre fill existing data
        dialogView.findViewById<TextInputEditText>(
            com.example.to_doapp.R.id.etTaskTitle).setText(task.title)
        dialogView.findViewById<TextInputEditText>(
            com.example.to_doapp.R.id.etTaskDescription).setText(task.description)
        dialogView.findViewById<TextInputEditText>(
            com.example.to_doapp.R.id.etDueDate).setText(task.dueDate)
        dialogView.findViewById<TextInputEditText>(
            com.example.to_doapp.R.id.etPriority).setText(task.priority)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Task")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val title = dialogView.findViewById<TextInputEditText>(
                    com.example.to_doapp.R.id.etTaskTitle).text.toString().trim()
                val desc = dialogView.findViewById<TextInputEditText>(
                    com.example.to_doapp.R.id.etTaskDescription).text.toString().trim()
                val dueDate = dialogView.findViewById<TextInputEditText>(
                    com.example.to_doapp.R.id.etDueDate).text.toString().trim()
                val priority = dialogView.findViewById<TextInputEditText>(
                    com.example.to_doapp.R.id.etPriority).text.toString().trim()

                if (title.isNotEmpty()) {
                    viewModel.updateTask(
                        task.copy(
                            title = title,
                            description = desc,
                            dueDate = dueDate,
                            priority = priority.ifEmpty { "Medium" }
                        )
                    )
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteTaskDialog(task: Task) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Task")
            .setMessage("Delete '${task.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteTask(task)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}