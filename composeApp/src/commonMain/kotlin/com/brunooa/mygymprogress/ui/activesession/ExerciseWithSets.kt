package com.brunooa.mygymprogress.ui.activesession

import com.brunooa.mygymprogress.domain.model.Exercise
import com.brunooa.mygymprogress.domain.model.WorkoutSet

data class ExerciseWithSets(
    val exercise: Exercise,
    val sets: List<WorkoutSet>
)
