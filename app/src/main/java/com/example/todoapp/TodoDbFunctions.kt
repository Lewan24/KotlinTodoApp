package com.example.todoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDbFunctions {
    @Query("SELECT * FROM todoitem")
    fun getAll(): List<TodoItem>

    @Insert
    fun insert(vararg newtodo: TodoItem)

    @Delete
    fun delete(todoitem: TodoItem)

    @Update
    fun update(todoitem: TodoItem)
}
