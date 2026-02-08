package com.brunooa.mygymprogress.domain.model

data class WorkoutSet(
    val id: String,
    val sessionId: String,
    val exerciseId: String,
    val weight: Double,
    val reps: Int,
    val isCompleted: Boolean
)
