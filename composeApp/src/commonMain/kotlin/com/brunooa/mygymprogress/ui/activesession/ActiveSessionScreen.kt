package com.brunooa.mygymprogress.ui.activesession

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.brunooa.mygymprogress.domain.repository.IWorkoutRepository
import com.brunooa.mygymprogress.ui.activesession.components.AddExerciseDialog
import com.brunooa.mygymprogress.ui.activesession.components.ExerciseItem
import com.brunooa.mygymprogress.ui.activesession.components.SessionHeader
import com.brunooa.mygymprogress.ui.components.GymButton
import com.brunooa.mygymprogress.di.koinInject

class ActiveSessionScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val repository: IWorkoutRepository = koinInject()
        val viewModel = remember(navigator) {
            ActiveSessionViewModel(repository, navigator)
        }
        val uiState by viewModel.uiState.collectAsState()
        var showAddExerciseDialog by remember { mutableStateOf(false) }
        var exercises by remember { mutableStateOf<List<com.brunooa.mygymprogress.domain.model.Exercise>>(emptyList()) }
        
        LaunchedEffect(Unit) {
            repository.getExercises().collect {
                exercises = it
            }
        }
        
        Scaffold(
            topBar = {
                SessionHeader(
                    elapsedTime = uiState.elapsedTime,
                    onFinishClick = {
                        viewModel.finishSession("")
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (uiState.exercises.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        GymButton(
                            text = "Adicionar Exercício",
                            onClick = { showAddExerciseDialog = true },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
                    ) {
                        items(uiState.exercises) { exerciseWithSets ->
                            ExerciseItem(
                                exercise = exerciseWithSets.exercise,
                                sets = exerciseWithSets.sets,
                                onSetUpdate = { setId, weight, reps, isCompleted ->
                                    viewModel.updateSet(setId, weight, reps, isCompleted)
                                }
                            )
                        }
                        
                        item {
                            GymButton(
                                text = "Adicionar Exercício",
                                onClick = { showAddExerciseDialog = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            )
                        }
                    }
                }
            }
        }
        
        if (showAddExerciseDialog) {
            AddExerciseDialog(
                exercises = exercises,
                onDismiss = { showAddExerciseDialog = false },
                onExerciseSelected = { exerciseId ->
                    viewModel.addExercise(exerciseId)
                }
            )
        }
    }
}
