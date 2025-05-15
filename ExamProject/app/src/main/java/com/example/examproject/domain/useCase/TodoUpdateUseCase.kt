package com.example.examproject.domain.useCase

import com.example.examproject.data.repository.TodoRepository
import com.example.examproject.domain.Todo

class TodoUpdateUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo): Todo = repository.updateTodo(todo)
}