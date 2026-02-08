package com.brunooa.mygymprogress.data.repository

import com.brunooa.mygymprogress.data.local.dao.ExerciseDao
import com.brunooa.mygymprogress.data.local.dao.WorkoutSessionDao
import com.brunooa.mygymprogress.data.local.dao.WorkoutSetDao
import com.brunooa.mygymprogress.data.local.entity.ExerciseEntity
import com.brunooa.mygymprogress.data.local.entity.WorkoutSessionEntity
import com.brunooa.mygymprogress.data.local.entity.WorkoutSetEntity
import com.brunooa.mygymprogress.domain.model.Exercise
import com.brunooa.mygymprogress.domain.model.MuscleGroup
import com.brunooa.mygymprogress.domain.model.WorkoutSession
import com.brunooa.mygymprogress.domain.model.WorkoutSet
import com.brunooa.mygymprogress.domain.repository.IWorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import java.util.UUID

class WorkoutRepositoryImpl(
    private val exerciseDao: ExerciseDao,
    private val sessionDao: WorkoutSessionDao,
    private val setDao: WorkoutSetDao
) : IWorkoutRepository {
    
    override fun getExercises(): Flow<List<Exercise>> {
        return exerciseDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun createSession(): String {
        val sessionId = UUID.randomUUID().toString()
        val now = kotlinx.datetime.Clock.System.now()
        val session = WorkoutSessionEntity(
            id = sessionId,
            startTime = now.toEpochMilliseconds(),
            endTime = null,
            note = ""
        )
        sessionDao.insert(session)
        return sessionId
    }
    
    private fun Instant.toEpochMilliseconds(): Long {
        return this.epochSeconds * 1000 + (this.nanosecondsOfSecond / 1_000_000)
    }
    
    override suspend fun addSet(sessionId: String, exerciseId: String, weight: Double, reps: Int) {
        val setId = UUID.randomUUID().toString()
        val set = WorkoutSetEntity(
            id = setId,
            sessionId = sessionId,
            exerciseId = exerciseId,
            weight = weight,
            reps = reps,
            isCompleted = false
        )
        setDao.insert(set)
    }
    
    override suspend fun updateSet(setId: String, weight: Double, reps: Int, isCompleted: Boolean) {
        val existingSet = setDao.getById(setId)
        if (existingSet != null) {
            val updatedSet = existingSet.copy(
                weight = weight,
                reps = reps,
                isCompleted = isCompleted
            )
            setDao.update(updatedSet)
        }
    }
    
    override suspend fun finishSession(sessionId: String, note: String) {
        val session = sessionDao.getByIdSync(sessionId)
        if (session != null) {
            val now = kotlinx.datetime.Clock.System.now()
            val updatedSession = session.copy(
                endTime = now.toEpochMilliseconds(),
                note = note
            )
            sessionDao.update(updatedSession)
        }
    }
    
    override fun getSessions(): Flow<List<WorkoutSession>> {
        return sessionDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override fun getSessionSets(sessionId: String): Flow<List<WorkoutSet>> {
        return setDao.getBySessionId(sessionId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    private fun ExerciseEntity.toDomain(): Exercise {
        return Exercise(
            id = id,
            name = name,
            muscleGroup = MuscleGroup.valueOf(muscleGroup)
        )
    }
    
    private fun WorkoutSessionEntity.toDomain(): WorkoutSession {
        return WorkoutSession(
            id = id,
            startTime = startTime.toInstant(),
            endTime = endTime?.toInstant(),
            note = note
        )
    }
    
    private fun Long.toInstant(): Instant {
        val seconds = this / 1000
        return Instant.fromEpochSeconds(seconds, 0)
    }
    
    private fun WorkoutSetEntity.toDomain(): WorkoutSet {
        return WorkoutSet(
            id = id,
            sessionId = sessionId,
            exerciseId = exerciseId,
            weight = weight,
            reps = reps,
            isCompleted = isCompleted
        )
    }
}
