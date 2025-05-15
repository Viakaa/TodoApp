package com.example.examproject.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.examproject.R
import com.example.examproject.domain.Todo

@Composable
fun TodoItem(
    todo: Todo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.little_padding))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.secondary_color)
        )
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.todo,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.white)
                )
            }

            if (!todo.completed) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = stringResource(id = R.string.edit),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.big_padding)),
                    tint = colorResource(id = R.color.white)
                )
            }
        }
    }
}
