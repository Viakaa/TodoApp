package com.example.examproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examproject.domain.Todo
import com.example.examproject.domain.useCase.TodoGetUseCase
import com.example.examproject.domain.useCase.TodoUpdateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoDetailsViewModel(
    private val getTodoByIdUseCase: TodoGetUseCase,
    private val updateTodoUseCase: TodoUpdateUseCase
) : ViewModel() {

    private val _todo = MutableStateFlow<Todo?>(null)
    val todo: StateFlow<Todo?> = _todo

    private val _editedText = MutableStateFlow("")
    val editedText: StateFlow<String> = _editedText

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _completed = MutableStateFlow(false)
    val completed: StateFlow<Boolean> = _completed

    fun setCompleted(value: Boolean) {
        _completed.value = value
    }

    fun loadTodo(id: Int) {
        viewModelScope.launch {
            try {
                val loadedTodo = getTodoByIdUseCase(id)
                _todo.value = loadedTodo
                _editedText.value = loadedTodo.todo
                _completed.value = loadedTodo.completed
                _isEditing.value = !loadedTodo.completed
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load todo: ${e.localizedMessage}"
            }
        }
    }


    fun onTextChanged(newText: String) {
        _editedText.value = newText
    }

    fun saveChanges(onSaved: () -> Unit) {
        val currentTodo = _todo.value ?: return
        if (currentTodo.todo == _editedText.value) {
            onSaved()
            return
        }

        viewModelScope.launch {
            _isSaving.value = true
            try {
                val updatedTodo = currentTodo.copy(
                    todo = _editedText.value,
                    completed = _completed.value
                )
                updateTodoUseCase(updatedTodo)
                _todo.value = updatedTodo
                _isEditing.value = !updatedTodo.completed
                _errorMessage.value = null
                onSaved()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to save todo: ${e.localizedMessage}"
            } finally {
                _isSaving.value = false
            }
        }
    }
    }

