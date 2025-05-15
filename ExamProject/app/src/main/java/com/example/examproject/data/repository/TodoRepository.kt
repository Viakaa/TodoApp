package com.example.examproject.data.repository

import com.example.examproject.domain.Todo

interface TodoRepository {
    suspend fun getTodos(): List<Todo>
    suspend fun updateTodo(todo: Todo): Todo
    suspend fun addTodo(todo: Todo): Todo
    suspend fun getTodoById(id: Int): Todo
}