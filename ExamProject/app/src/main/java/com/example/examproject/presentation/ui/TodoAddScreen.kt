package com.example.examproject.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.examproject.domain.Todo
import com.example.examproject.presentation.ui.viewmodel.TodoListViewModel
import com.example.examproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAddScreen(
    listViewModel: TodoListViewModel,
    onBack: () -> Unit
) {
    var todoText by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.add_todo)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.horizontal_padding)
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = todoText,
                onValueChange = { todoText = it },
                label = { Text(stringResource(id = R.string.todo_label)) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3,
                enabled = !isSaving
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))

            Button(
                onClick = {
                    isSaving = true
                    val todo = Todo(
                        id = 0,
                        todo = todoText,
                        completed = false,
                        userId = 1
                    )
                    listViewModel.addTodo(
                        todo,
                        onComplete = {
                            isSaving = false
                            onBack()
                        }
                    )
                },
                enabled = !isSaving && todoText.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.secondary_color),
                    contentColor = colorResource(id = R.color.tertiary_color)
                )
            ) {
                Text(if (isSaving) stringResource(id = R.string.saving_text) else stringResource(id = R.string.save_button))
            }
        }
    }
}
