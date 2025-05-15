package com.example.examproject.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examproject.R
import com.example.examproject.presentation.ui.viewmodel.TodoDetailsViewModel
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailsScreen(
    todoId: Int,
    viewModel: TodoDetailsViewModel = viewModel(),
    onBack: () -> Unit
) {
    val todo by viewModel.todo.collectAsState()
    val editedText by viewModel.editedText.collectAsState()
    val isEditing by viewModel.isEditing.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    val completed by viewModel.completed.collectAsState()

    var showExitDialog by remember { mutableStateOf(false) }

    LaunchedEffect(todoId) {
        viewModel.loadTodo(todoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.todo_details)) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isEditing && editedText != todo?.todo) {
                            showExitDialog = true
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        todo?.let { currentTodo ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.horizontal_padding),
                        vertical = 0.dp
                    )
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = editedText,
                        onValueChange = { viewModel.onTextChanged(it) },
                        label = { Text(stringResource(id = R.string.edit_todo)) },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.little_padding)))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.little_padding))
                    ) {
                        Checkbox(
                            checked = completed,
                            onCheckedChange = { viewModel.setCompleted(it) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(id = R.string.completed))
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.little_padding)))

                    val errorMessage by viewModel.errorMessage.collectAsState()
                    errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))

                    Button(
                        onClick = {
                            viewModel.saveChanges {
                                onBack()
                            }
                        },
                        enabled = !isSaving,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.secondary_color),
                            contentColor = colorResource(id = R.color.tertiary_color)
                        )
                    ) {
                        Text(
                            if (isSaving)
                                stringResource(id = R.string.saving_text)
                            else
                                stringResource(id = R.string.save_button)
                        )
                    }
                } else {
                    Text(
                        text = currentTodo.todo,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (showExitDialog) {
                AlertDialog(
                    onDismissRequest = { showExitDialog = false },
                    title = { Text(stringResource(id = R.string.exit_dialog_title)) },
                    text = { Text(stringResource(id = R.string.exit_dialog_text)) },
                    confirmButton = {
                        TextButton(onClick = {
                            showExitDialog = false
                            onBack()
                        }) {
                            Text(stringResource(id = R.string.exit_dialog_yes))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showExitDialog = false
                        }) {
                            Text(stringResource(id = R.string.exit_dialog_no))
                        }
                    }
                )
            }
        }
    }
}
