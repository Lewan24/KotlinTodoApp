package com.example.todoapp

data class TodoItem (
    val id: Int,
    var title: String,
    var description: String,
    var isCompleted: Boolean
)