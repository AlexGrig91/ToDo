package com.example.todolistapp

import android.os.Build
import android.view.animation.Transformation
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel : ViewModel() {

    val todoDao = MainApplication.todoDatabase.getTodoDao()
    private val _reversedTodoList = MediatorLiveData<List<Todo>>()
    val todoList: LiveData<List<Todo>> = _reversedTodoList

    init {
        // Наблюдаем за изменениями в базе данных и переворачиваем список
        _reversedTodoList.addSource(todoDao.getAllTodo()) { todos ->
            _reversedTodoList.value = todos.reversed() // Переворачиваем список
        }
    }

    //val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()


    @RequiresApi(Build.VERSION_CODES.O)
    fun addTodo(title: String) {

        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(
                Todo(
                    title = title,
                    createdAt = Date.from(Instant.now())
                )
            )
        }

    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }

    }
}