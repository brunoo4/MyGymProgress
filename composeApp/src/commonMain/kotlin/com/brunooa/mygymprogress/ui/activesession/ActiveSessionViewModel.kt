package com.brunooa.mygymprogress.ui.activesession

import cafe.adriel.voyager.navigator.Navigator
import com.brunooa.mygymprogress.domain.model.Exercise
import com.brunooa.mygymprogress.domain.model.WorkoutSet
import com.brunooa.mygymprogress.domain.repository.IWorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

class ActiveSessionViewModel(
    private val repository: IWorkoutRepository,
    private val navigator: Navigator
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private val _uiState = MutableStateFlow<ActiveSessionUiState>(ActiveSessionUiState())
    val uiState: StateFlow<ActiveSessionUiState> = _uiState.asStateFlow()
    
    private var timerJob: kotlinx.coroutines.Job? = null
    
    init {
        startSession()
        updateExercisesList()
    }
    
    fun startSession() {
        viewModelScope.launch {
            val sessionId = repository.createSession()
            val startTime = Clock.System.now()
            _uiState.value = _uiState.value.copy(
                sessionId = sessionId,
                startTime = startTime
            )
            startTimer()
        }
    }
    
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                val startTime = _uiState.value.startTime
                if (startTime != null) {
                    val now = Clock.System.now()
                    // Calculate elapsed time in seconds and convert to kotlin.time.Duration
                    val elapsedSeconds = (now.epochSeconds - startTime.epochSeconds).toDouble()
                    val elapsed = elapsedSeconds.seconds
                    _uiState.value = _uiState.value.copy(elapsedTime = elapsed)
                }
                delay(1000)
            }
        }
    }
    
    fun addExercise(exerciseId: String) {
        viewModelScope.launch {
            val sessionId = _uiState.value.sessionId ?: return@launch
            repository.addSet(sessionId, exerciseId, 0.0, 0)
            updateExercisesList()
        }
    }
    
    fun updateSet(setId: String, weight: Double, reps: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateSet(setId, weight, reps, isCompleted)
            updateExercisesList()
        }
    }
    
    fun finishSession(note: String) {
        viewModelScope.launch {
            val sessionId = _uiState.value.sessionId ?: return@launch
            repository.finishSession(sessionId, note)
            timerJob?.cancel()
            navigator.pop()
        }
    }
    
    private fun updateExercisesList() {
        viewModelScope.launch {
            val sessionId = _uiState.value.sessionId ?: return@launch
            val exercises = repository.getExercises()
            val sets = repository.getSessionSets(sessionId)
            
            exercises.collect { exerciseList ->
                sets.collect { setList ->
                    val exercisesWithSets = exerciseList.map { exercise ->
                        val exerciseSets = setList.filter { it.exerciseId == exercise.id }
                        ExerciseWithSets(exercise, exerciseSets)
                    }.filter { it.sets.isNotEmpty() }
                    
                    _uiState.value = _uiState.value.copy(exercises = exercisesWithSets)
                }
            }
        }
    }
}
