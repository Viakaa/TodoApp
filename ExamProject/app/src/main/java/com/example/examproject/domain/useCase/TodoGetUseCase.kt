package com.example.examproject.domain.useCase

import com.example.examproject.data.repository.TodoRepository
import com.example.examproject.domain.Todo

class TodoGetUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Int): Todo {
        return repository.getTodoById(id)
    }
}
