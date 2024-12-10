package com.example.todolistapp

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

object TodoManagerDeleteSoon {

    private val todoList = mutableListOf<Todo>()

    fun getAllTodo(): List<Todo>{
        return todoList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTodo(title: String){
        todoList.add(Todo(System.currentTimeMillis().toInt(), title, Date.from(Instant.now())))
    }

    fun deleteTodo(id: Int){
        todoList.removeIf{
            it.id==id
        }
    }
}