package com.brunooa.mygymprogress.ui.activesession

import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlinx.datetime.Instant

data class ActiveSessionUiState(
    val sessionId: String? = null,
    val startTime: Instant? = null,
    val elapsedTime: Duration = ZERO,
    val exercises: List<ExerciseWithSets> = emptyList(),
    val isFinished: Boolean = false
)
