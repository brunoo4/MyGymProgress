package com.brunooa.mygymprogress.domain.model

import kotlinx.datetime.Instant

data class WorkoutSession(
    val id: String,
    val startTime: Instant,
    val endTime: Instant?,
    val note: String
)
