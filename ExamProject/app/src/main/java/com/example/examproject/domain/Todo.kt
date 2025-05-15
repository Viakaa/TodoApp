package com.example.examproject.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Todo(
    val id: Int,
    @Json(name = "todo") val todo: String,
    val completed: Boolean,
    val userId: Int
)
