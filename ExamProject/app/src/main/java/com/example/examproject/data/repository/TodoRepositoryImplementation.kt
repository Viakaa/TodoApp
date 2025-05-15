package com.example.examproject.data.repository

import com.example.examproject.domain.Todo
import com.example.examproject.data.TodoLocalDataSource
import com.example.examproject.data.TodoRemoteDataSource

class TodoRepositoryImplementation (private val todoLocalDataSource: TodoLocalDataSource,
                                    private val todoRemoteDataSource: TodoRemoteDataSource
) : TodoRepository {

    override suspend fun getTodos(): List<Todo> {
        return try {
            val todos = todoRemoteDataSource.fetchTodos()
            todoLocalDataSource.saveTodos(todos)
            todos
        } catch (e: Exception) {
            todoLocalDataSource.getTodos()
        }
    }

    override suspend fun updateTodo(todo: Todo): Todo {
        val updated = todoRemoteDataSource.updateTodo(todo)
        todoLocalDataSource.updateTodo(updated)
        return updated
    }
    override suspend fun addTodo(todo: Todo): Todo {
        val added = todoRemoteDataSource.addTodo(todo)
        val localAdded = todoLocalDataSource.addTodo(added)
        return localAdded
    }

    override suspend fun getTodoById(id: Int): Todo {
        return todoLocalDataSource.getTodoById(id)
    }
}