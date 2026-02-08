package com.brunooa.mygymprogress.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.brunooa.mygymprogress.data.local.entity.WorkoutSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {
    @Query("SELECT * FROM workout_sessions ORDER BY startTime DESC")
    fun getAll(): Flow<List<WorkoutSessionEntity>>
    
    @Query("SELECT * FROM workout_sessions WHERE id = :id")
    fun getById(id: String): Flow<WorkoutSessionEntity?>
    
    @Query("SELECT * FROM workout_sessions WHERE id = :id")
    suspend fun getByIdSync(id: String): WorkoutSessionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: WorkoutSessionEntity)
    
    @Update
    suspend fun update(session: WorkoutSessionEntity)
}
