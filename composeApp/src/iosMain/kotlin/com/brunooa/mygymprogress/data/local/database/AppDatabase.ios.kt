package com.brunooa.mygymprogress.data.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.brunooa.mygymprogress.data.local.database.AppDatabase
import com.brunooa.mygymprogress.data.local.database.createDriver

actual fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val driver = createDriver()
    return Room.databaseBuilder(
        driver,
        AppDatabase::class,
        "mygym_db"
    )
}
