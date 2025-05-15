package com.example.examproject.data

import com.example.examproject.domain.Todo
import com.example.examproject.data.model.TodoDao
import com.example.examproject.data.model.TodoEntity

class TodoLocalDataSource(private val dao: TodoDao) {
    suspend fun getTodos(): List<Todo> =
        dao.getAll().map { it.toDomain() }
    suspend fun saveTodos(todos: List<Todo>) =
        dao.insertAll(todos.map { TodoEntity.fromDomain(it) })
    suspend fun updateTodo(todo: Todo) =
        dao.update(TodoEntity.fromDomain(todo))
    suspend fun addTodo(todo: Todo): Todo {
        val entity = TodoEntity.fromDomain(todo, isNew = true)
        val id = dao.insert(entity)
        return todo.copy(id = id.toInt())
    }
    suspend fun getTodoById(id: Int): Todo {
        return dao.getTodoById(id).toDomain()
    }

}