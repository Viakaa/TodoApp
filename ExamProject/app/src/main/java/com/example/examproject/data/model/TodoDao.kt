package com.example.examproject.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    suspend fun getAll(): List<TodoEntity>

    @Insert
    suspend fun insert(todo: TodoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<TodoEntity>)

    @Update
    suspend fun update(todo: TodoEntity)

    @Query("UPDATE todos SET completed = :completed WHERE id = :id")
    suspend fun updateCompleted(id: Int, completed: Boolean)

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoEntity
}