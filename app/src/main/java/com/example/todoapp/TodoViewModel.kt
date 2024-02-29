package com.example.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val db: TodoDbFunctions
    private var allTodoItems: MutableLiveData<List<TodoItem>>

    init {
        db = AppDatabase.getInstance(app).todoDao()
        allTodoItems = MutableLiveData()
    }

    fun getAllTodosAsObservers() : MutableLiveData<List<TodoItem>>{
        reloadTodos()

        return allTodoItems
    }

    fun reloadTodos() = viewModelScope.launch(Dispatchers.IO){
        allTodoItems.postValue(db.getAll())
    }

    fun insert(todoItem: TodoItem) = viewModelScope.launch(Dispatchers.IO){
        db.insert(todoItem)
        reloadTodos()
    }

    fun update(todoItem: TodoItem) = viewModelScope.launch(Dispatchers.IO){
        db.update(todoItem)
        reloadTodos()
    }

    fun delete(todoItem: TodoItem) = viewModelScope.launch(Dispatchers.IO){
        db.delete(todoItem)
        reloadTodos()
    }
}
