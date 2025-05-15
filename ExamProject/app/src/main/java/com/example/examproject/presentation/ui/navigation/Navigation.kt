package com.example.examproject.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.examproject.presentation.ui.TodoAddScreen
import com.example.examproject.presentation.ui.TodoListScreen
import com.example.examproject.presentation.ui.TodoDetailsScreen
import com.example.examproject.presentation.ui.viewmodel.TodoDetailsViewModel
import com.example.examproject.presentation.ui.viewmodel.TodoListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "todo_list") {
        composable("todo_list") {
            val listViewModel = koinViewModel<TodoListViewModel>()
            TodoListScreen(
                viewModel = listViewModel,
                onTodoClick = { id -> navController.navigate("todo_details/$id") },
                onAddClick = { navController.navigate("add_todo") }
            )
        }

        composable("todo_details/{todoId}") { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")?.toInt() ?: 0
            val detailsViewModel = koinViewModel<TodoDetailsViewModel>()
            TodoDetailsScreen(
                todoId = todoId,
                viewModel = detailsViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("add_todo") {
            val listViewModel = koinViewModel<TodoListViewModel>()
            TodoAddScreen(
                listViewModel = listViewModel,
                onBack = { navController.popBackStack() }
            )
        }

    }
}

