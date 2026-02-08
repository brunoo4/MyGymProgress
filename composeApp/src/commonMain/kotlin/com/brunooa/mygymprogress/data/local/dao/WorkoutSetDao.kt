package com.brunooa.mygymprogress.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.brunooa.mygymprogress.data.local.entity.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSetDao {
    @Query("SELECT * FROM workout_sets WHERE sessionId = :sessionId ORDER BY id ASC")
    fun getBySessionId(sessionId: String): Flow<List<WorkoutSetEntity>>
    
    @Query("SELECT * FROM workout_sets WHERE id = :id")
    suspend fun getById(id: String): WorkoutSetEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(set: WorkoutSetEntity)
    
    @Update
    suspend fun update(set: WorkoutSetEntity)
}
