package com.example.examproject.domain.useCase

import com.example.examproject.data.repository.TodoRepository
import com.example.examproject.domain.Todo

class TodoAddUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo): Todo {
        return repository.addTodo(todo)
    }
}
