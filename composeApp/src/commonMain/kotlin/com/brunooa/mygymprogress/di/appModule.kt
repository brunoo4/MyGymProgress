package com.brunooa.mygymprogress.di

import com.brunooa.mygymprogress.data.local.dao.ExerciseDao
import com.brunooa.mygymprogress.data.local.dao.WorkoutSessionDao
import com.brunooa.mygymprogress.data.local.dao.WorkoutSetDao
import com.brunooa.mygymprogress.data.local.database.AppDatabase
import com.brunooa.mygymprogress.data.local.database.createDatabaseBuilder
import com.brunooa.mygymprogress.data.local.database.seedDatabase
import com.brunooa.mygymprogress.data.repository.WorkoutRepositoryImpl
import com.brunooa.mygymprogress.domain.repository.IWorkoutRepository
import com.brunooa.mygymprogress.ui.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single<AppDatabase> {
        val database = createDatabaseBuilder().build()
        // Seed database on first run
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
        scope.launch {
            val exerciseDao = database.exerciseDao()
            val exercises = exerciseDao.getAll().first()
            if (exercises.isEmpty()) {
                seedDatabase(exerciseDao)
            }
        }
        database
    }
    
    single<ExerciseDao> {
        get<AppDatabase>().exerciseDao()
    }
    
    single<WorkoutSessionDao> {
        get<AppDatabase>().workoutSessionDao()
    }
    
    single<WorkoutSetDao> {
        get<AppDatabase>().workoutSetDao()
    }
    
    single<IWorkoutRepository> {
        WorkoutRepositoryImpl(
            exerciseDao = get(),
            sessionDao = get(),
            setDao = get()
        )
    }
    
    factory<HomeViewModel> {
        HomeViewModel(repository = get())
    }
}
