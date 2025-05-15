package com.example.examproject.data

import com.example.examproject.domain.Todo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoResponse(
    val todos: List<Todo>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
