package com.brunooa.mygymprogress.data.local.database

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun createDriver(): SQLiteDriver {
    val databasePath = File(System.getProperty("java.io.tmpdir"), "mygym_db.db")
    return BundledSQLiteDriver().apply {
        open(databasePath.absolutePath)
    }
}
