    package com.example.examproject.domain.useCase

    import com.example.examproject.data.repository.TodoRepository
    import com.example.examproject.domain.Todo

    class TodoUseCase(private val repository: TodoRepository) {
        suspend operator fun invoke(): List<Todo> = repository.getTodos()
    }