package com.brunooa.mygymprogress.ui.activesession.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brunooa.mygymprogress.domain.model.Exercise
import com.brunooa.mygymprogress.ui.components.GymButton

@Composable
fun AddExerciseDialog(
    exercises: List<Exercise>,
    onDismiss: () -> Unit,
    onExerciseSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Adicionar Exercício",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            LazyColumn {
                items(exercises) { exercise ->
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onExerciseSelected(exercise.id)
                                onDismiss()
                            }
                            .padding(16.dp)
                    )
                }
            }
        },
        confirmButton = {
            GymButton(
                text = "Cancelar",
                onClick = onDismiss
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}
