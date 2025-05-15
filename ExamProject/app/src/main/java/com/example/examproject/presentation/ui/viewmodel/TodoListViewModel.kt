package com.example.examproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examproject.domain.Todo
import com.example.examproject.domain.useCase.TodoAddUseCase
import com.example.examproject.domain.useCase.TodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val getTodosUseCase: TodoUseCase,
    private val addTodoUseCase: TodoAddUseCase
) : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadTodos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _todos.value = getTodosUseCase()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun addTodo(todo: Todo, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val addedTodo = addTodoUseCase(todo)
                val updatedList = _todos.value.toMutableList().apply {
                    add(addedTodo)
                }
                _todos.value = updatedList
                onComplete()
            } catch (e: Exception) {
            }
        }
    }

}
