package com.example.todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.MainlayoutBinding

class MainActivity : ComponentActivity() {
    private lateinit var todoAdapter: TodoAdapter
    private val todoList = mutableListOf<TodoItem>()

    private lateinit var binding: MainlayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainlayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoAdapter(todoList)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = todoAdapter

        binding.bAddTodoButton.setOnClickListener {
            val newTodoTitle = binding.etNewTodoTitle.text.toString().trim()
            val newTodoDescription = binding.etNewTodoDescription.text.toString().trim()

            if (newTodoTitle.isNotEmpty()) {
                val newTodoItem = TodoItem(
                    id = todoList.size + 1,
                    title = newTodoTitle,
                    description = if (newTodoDescription.isNotEmpty()) newTodoDescription else "",
                    isCompleted = false
                )
                todoList.add(newTodoItem)

                todoAdapter.notifyItemInserted(todoList.size - 1)

                binding.etNewTodoTitle.text.clear()
                binding.etNewTodoDescription.text.clear()

                Toast.makeText(this, "Added $newTodoTitle", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
        }
    }
}
