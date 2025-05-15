    package com.example.examproject.data.model

    import androidx.room.Entity
    import androidx.room.PrimaryKey
    import com.example.examproject.domain.Todo

    @Entity(tableName = "todos")
    data class TodoEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val todo: String,
        val completed: Boolean,
        val userId: Int
    ) {
        fun toDomain() = Todo(id, todo, completed, userId)
        companion object {
            fun fromDomain(todo: Todo, isNew: Boolean = false): TodoEntity {
                return if (isNew) {
                    TodoEntity(0, todo.todo, todo.completed, todo.userId)
                } else {
                    TodoEntity(todo.id, todo.todo, todo.completed, todo.userId)
                }
            }
        }

    }