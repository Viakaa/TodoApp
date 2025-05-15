package com.example.examproject.data

import com.example.examproject.domain.Todo
import com.example.examproject.domain.TodoUpdate
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.POST
import retrofit2.http.Path


interface TodoApiService {
    @POST("todos/add")
    suspend fun addTodo(@Body todo: Todo): Todo

    @GET("todos")
    suspend fun getTodos(): TodoListResponse

    @PUT("todos/{id}")
    suspend fun updateTodo(@Path("id") id: Int, @Body todoUpdate: TodoUpdate): Todo

}

data class TodoListResponse(val todos: List<Todo>)