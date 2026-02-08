package com.brunooa.mygymprogress.domain.repository

import com.brunooa.mygymprogress.domain.model.Exercise
import com.brunooa.mygymprogress.domain.model.WorkoutSession
import com.brunooa.mygymprogress.domain.model.WorkoutSet
import kotlinx.coroutines.flow.Flow

interface IWorkoutRepository {
    fun getExercises(): Flow<List<Exercise>>
    suspend fun createSession(): String
    suspend fun addSet(sessionId: String, exerciseId: String, weight: Double, reps: Int)
    suspend fun updateSet(setId: String, weight: Double, reps: Int, isCompleted: Boolean)
    suspend fun finishSession(sessionId: String, note: String)
    fun getSessions(): Flow<List<WorkoutSession>>
    fun getSessionSets(sessionId: String): Flow<List<WorkoutSet>>
}
