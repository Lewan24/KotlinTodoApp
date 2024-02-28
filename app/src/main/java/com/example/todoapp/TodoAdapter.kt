package com.example.todoapp

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val todoItems: MutableList<TodoItem>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnLongClickListener {
        val todoItemName: TextView = itemView.findViewById(R.id.tvItemTitle)
        val todoItemDescription: TextView = itemView.findViewById(R.id.tvItemDescription)
        val todoItemIsCompleted: CheckBox = itemView.findViewById(R.id.cbItemIsCompleted)

        init {
            itemView.setOnLongClickListener(this)

            todoItemIsCompleted.setOnClickListener{
                val position = bindingAdapterPosition
                val todoItem = todoItems[position]

                todoItem.isCompleted = !todoItem.isCompleted
                notifyItemChanged(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = bindingAdapterPosition
            val todoItem = todoItems[position]

            val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.edit_todoitem_dialog, null)

            dialogView.findViewById<EditText>(R.id.eteditTodoTitleText).text.append(todoItem.title)
            dialogView.findViewById<EditText>(R.id.eteditTodoDescriptionText).text.append(todoItem.description)

            val dialog = AlertDialog.Builder(itemView.context)
                .setView(dialogView)
                .setTitle("Edit Todo Item")
                .create()

            val btnSave = dialogView.findViewById<Button>(R.id.btnSave)
            btnSave.setOnClickListener {
                val title = dialogView.findViewById<EditText>(R.id.eteditTodoTitleText).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.eteditTodoDescriptionText).text.toString()

                val editedTask = todoItem.copy(title = title, description = description)
                editTodoItem(position, editedTask)
                dialog.dismiss()
            }

            val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            val btnDelete = dialogView.findViewById<Button>(R.id.btnDelete)
            btnDelete.setOnClickListener {
                if (todoItem.isCompleted)
                    removeTodoItem(position)
                else Toast.makeText(this.itemView.context, "The todo item should be completed before deleting", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            dialog.show()

            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun getItemCount(): Int = todoItems.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTask = todoItems[position]

        holder.todoItemName.text = currentTask.title
        holder.todoItemDescription.text = currentTask.description
        holder.todoItemIsCompleted.isChecked = currentTask.isCompleted
    }

    fun removeTodoItem(position: Int) {
        todoItems.removeAt(position)
        notifyItemRemoved(position)
    }

    fun editTodoItem(position: Int, newTodo: TodoItem) {
        todoItems[position] = newTodo
        notifyItemChanged(position)
    }
}