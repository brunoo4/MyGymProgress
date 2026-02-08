package com.brunooa.mygymprogress.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brunooa.mygymprogress.data.local.database.AppDatabase

actual fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = getApplicationContext()
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "mygym_db"
    )
}
