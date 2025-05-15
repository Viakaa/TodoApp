package com.example.examproject.data

import com.example.examproject.domain.Todo
import com.example.examproject.domain.TodoUpdate

class TodoRemoteDataSource(private val api: TodoApiService) {
    suspend fun fetchTodos(): List<Todo> = api.getTodos().todos
    suspend fun updateTodo(todo: Todo): Todo {
        println("Updating TODO with id = ${todo.id}, text = ${todo.todo}")
        val update = TodoUpdate(todo = todo.todo, completed = todo.completed)
        return api.updateTodo(todo.id, update)
    }

    suspend fun addTodo(todo: Todo): Todo = api.addTodo(todo)
}

