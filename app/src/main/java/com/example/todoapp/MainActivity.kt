package com.example.todoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.MainlayoutBinding

class MainActivity : ComponentActivity() {
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var databaseViewModel: TodoViewModel

    private lateinit var binding: MainlayoutBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainlayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoAdapter()

        databaseViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        databaseViewModel.getAllTodosAsObservers().observe(this) {
            todoAdapter.setListData(ArrayList(it))
            todoAdapter.setViewModel(this.databaseViewModel)
            todoAdapter.notifyDataSetChanged()
        }

        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = todoAdapter

        binding.bAddTodoButton.setOnClickListener {
            val newTodoTitle = binding.etNewTodoTitle.text.toString().trim()
            val newTodoDescription = binding.etNewTodoDescription.text.toString().trim()

            if (newTodoTitle.isNotEmpty()) {
                val newTodoItem = TodoItem(
                    title = newTodoTitle,
                    description = newTodoDescription,
                    isCompleted = false
                )
                databaseViewModel.insert(newTodoItem)

                binding.etNewTodoTitle.text.clear()
                binding.etNewTodoDescription.text.clear()

                Toast.makeText(this, "Added $newTodoTitle", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
        }
    }
}
