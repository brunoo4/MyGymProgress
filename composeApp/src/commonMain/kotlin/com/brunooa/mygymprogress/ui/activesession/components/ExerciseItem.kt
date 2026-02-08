package com.brunooa.mygymprogress.ui.activesession.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brunooa.mygymprogress.domain.model.Exercise
import com.brunooa.mygymprogress.domain.model.WorkoutSet
import com.brunooa.mygymprogress.ui.components.GymCard
import com.brunooa.mygymprogress.ui.components.GymInput

@Composable
fun ExerciseItem(
    exercise: Exercise,
    sets: List<WorkoutSet>,
    onSetUpdate: (String, Double, Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    GymCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            sets.forEach { set ->
                SetRow(
                    set = set,
                    onUpdate = { weight, reps, isCompleted ->
                        onSetUpdate(set.id, weight, reps, isCompleted)
                    }
                )
            }
        }
    }
}

@Composable
private fun SetRow(
    set: WorkoutSet,
    onUpdate: (Double, Int, Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GymInput(
            value = set.weight.toString(),
            onValueChange = { value ->
                val weight = value.toDoubleOrNull() ?: 0.0
                onUpdate(weight, set.reps, set.isCompleted)
            },
            label = "Peso (kg)",
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        )
        
        GymInput(
            value = set.reps.toString(),
            onValueChange = { value ->
                val reps = value.toIntOrNull() ?: 0
                onUpdate(set.weight, reps, set.isCompleted)
            },
            label = "Reps",
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
        )
        
        Checkbox(
            checked = set.isCompleted,
            onCheckedChange = { isCompleted ->
                onUpdate(set.weight, set.reps, isCompleted)
            },
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
