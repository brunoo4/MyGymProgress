package com.brunooa.mygymprogress.data.local.database

import com.brunooa.mygymprogress.data.local.dao.ExerciseDao
import com.brunooa.mygymprogress.data.local.entity.ExerciseEntity
import com.brunooa.mygymprogress.domain.model.MuscleGroup
import java.util.UUID

suspend fun seedDatabase(exerciseDao: ExerciseDao) {
    val exercises = listOf(
        ExerciseEntity(
            id = UUID.randomUUID().toString(),
            name = "Supino",
            muscleGroup = MuscleGroup.CHEST.name
        ),
        ExerciseEntity(
            id = UUID.randomUUID().toString(),
            name = "Agachamento",
            muscleGroup = MuscleGroup.LEGS.name
        ),
        ExerciseEntity(
            id = UUID.randomUUID().toString(),
            name = "Terra",
            muscleGroup = MuscleGroup.BACK.name
        ),
        ExerciseEntity(
            id = UUID.randomUUID().toString(),
            name = "Flexão",
            muscleGroup = MuscleGroup.CHEST.name
        ),
        ExerciseEntity(
            id = UUID.randomUUID().toString(),
            name = "Desenvolvimento",
            muscleGroup = MuscleGroup.SHOULDERS.name
        ),
        ExerciseEntity(
            id = UUID.randomUUID().toString(),
            name = "Rosca Direta",
            muscleGroup = MuscleGroup.ARMS.name
        )
    )
    
    exerciseDao.insertAll(exercises)
}
