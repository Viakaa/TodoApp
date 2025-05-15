package com.example.examproject.di

import androidx.room.Room
import com.example.examproject.data.TodoApiService
import com.example.examproject.data.TodoLocalDataSource
import com.example.examproject.data.TodoRemoteDataSource
import com.example.examproject.data.model.TodoDatabase
import com.example.examproject.data.repository.TodoRepository
import com.example.examproject.data.repository.TodoRepositoryImplementation
import com.example.examproject.domain.useCase.*
import com.example.examproject.presentation.ui.viewmodel.TodoDetailsViewModel
import com.example.examproject.presentation.ui.viewmodel.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
    }

    single {
        Room.databaseBuilder(get(), TodoDatabase::class.java, "todos_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<TodoDatabase>().todoDao() }

    single { TodoLocalDataSource(get()) }
    single { TodoRemoteDataSource(get()) }

    single<TodoRepository> { TodoRepositoryImplementation(get(), get()) }

    single { TodoUseCase(get()) }
    single { TodoUpdateUseCase(get()) }
    single { TodoAddUseCase(get()) }
    single { TodoGetUseCase(get()) }

    viewModel { TodoListViewModel(get(), get()) }
    viewModel { TodoDetailsViewModel(get(), get()) }
}

