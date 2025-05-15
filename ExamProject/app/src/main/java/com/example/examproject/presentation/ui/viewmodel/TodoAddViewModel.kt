package com.example.examproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examproject.domain.Todo
import com.example.examproject.domain.useCase.TodoAddUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoAddViewModel(
    private val todoAddUseCase: TodoAddUseCase
) : ViewModel() {

    private val _todoText = MutableStateFlow("")
    val todoText: StateFlow<String> = _todoText

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    fun onTodoTextChanged(newText: String) {
        _todoText.value = newText
    }

    fun addTodo(userId: Int = 1, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val text = _todoText.value.trim()
        if (text.isEmpty()) return

        viewModelScope.launch {
            _isSaving.value = true
            try {
                val newTodo = Todo(
                    id = 1,
                    todo = text,
                    completed = false,
                    userId = userId
                )
                todoAddUseCase(newTodo)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            } finally {
                _isSaving.value = false
            }
        }
    }
}
