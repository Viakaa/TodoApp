package com.example.examproject
import com.example.examproject.data.TodoLocalDataSource
import com.example.examproject.data.TodoRemoteDataSource
import com.example.examproject.data.repository.TodoRepositoryImplementation
import com.example.examproject.domain.Todo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class RepositoryUnitTest {

    private lateinit var localDataSource: TodoLocalDataSource
    private lateinit var remoteDataSource: TodoRemoteDataSource
    private lateinit var repository: TodoRepositoryImplementation

    private val sampleTodos = listOf(
        Todo(id = 1, todo = "Blue bla boo di bla boo dai", completed = false, userId = 1),
        Todo(id = 2, todo = "Hello, its me< my friend", completed = true, userId = 1),
    )
    private val sampleTodo = Todo(id = 1, todo = "Test 1", completed = false, userId = 1)

    @Before
    fun setup() {
        localDataSource = mock()
        remoteDataSource = mock()
        repository = TodoRepositoryImplementation(localDataSource, remoteDataSource)
    }

    @Test
    fun `getTodos returns remote todos and saves locally`() = runBlocking {
        whenever(remoteDataSource.fetchTodos()).thenReturn(sampleTodos)

        val todos = repository.getTodos()

        verify(remoteDataSource).fetchTodos()
        verify(localDataSource).saveTodos(sampleTodos)
        assertEquals(sampleTodos, todos)
    }

    @Test
    fun `getTodos returns local todos if remote fetch fails`() = runBlocking {
        whenever(remoteDataSource.fetchTodos()).thenThrow(RuntimeException("Network error"))
        whenever(localDataSource.getTodos()).thenReturn(sampleTodos)

        val todos = repository.getTodos()

        verify(remoteDataSource).fetchTodos()
        verify(localDataSource).getTodos()
        assertEquals(sampleTodos, todos)
    }

    @Test
    fun `updateTodo updates remote and local, returns updated todo`() = runBlocking {
        whenever(remoteDataSource.updateTodo(sampleTodo)).thenReturn(sampleTodo)
        whenever(localDataSource.updateTodo(sampleTodo)).thenReturn(Unit)

        val updated = repository.updateTodo(sampleTodo)

        verify(remoteDataSource).updateTodo(sampleTodo)
        verify(localDataSource).updateTodo(sampleTodo)
        assertEquals(sampleTodo, updated)
    }

    @Test
    fun `addTodo adds to remote and local, returns added todo`() = runBlocking {
        whenever(remoteDataSource.addTodo(sampleTodo)).thenReturn(sampleTodo)
        whenever(localDataSource.addTodo(sampleTodo)).thenReturn(sampleTodo)

        val added = repository.addTodo(sampleTodo)

        verify(remoteDataSource).addTodo(sampleTodo)
        verify(localDataSource).addTodo(sampleTodo)
        assertEquals(sampleTodo, added)
    }

    @Test
    fun `getTodoById returns todo from local data source`() = runBlocking {
        whenever(localDataSource.getTodoById(1)).thenReturn(sampleTodo)

        val todo = repository.getTodoById(1)

        verify(localDataSource).getTodoById(1)
        assertEquals(sampleTodo, todo)
    }
}
