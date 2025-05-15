package com.example.examproject.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoUpdate(
    @Json(name = "todo") val todo: String,
    val completed: Boolean,
)
