package com.brunooa.mygymprogress.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brunooa.mygymprogress.data.local.dao.ExerciseDao
import com.brunooa.mygymprogress.data.local.dao.WorkoutSessionDao
import com.brunooa.mygymprogress.data.local.dao.WorkoutSetDao
import com.brunooa.mygymprogress.data.local.entity.ExerciseEntity
import com.brunooa.mygymprogress.data.local.entity.WorkoutSessionEntity
import com.brunooa.mygymprogress.data.local.entity.WorkoutSetEntity

@Database(
    entities = [
        ExerciseEntity::class,
        WorkoutSessionEntity::class,
        WorkoutSetEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun workoutSetDao(): WorkoutSetDao
}

expect fun createDatabaseBuilder(): androidx.room.RoomDatabase.Builder<AppDatabase>
