package com.brunooa.mygymprogress.data.local.database

import androidx.sqlite.SQLiteDriver

// Android doesn't need a custom driver, Room uses the built-in Android SQLite
// This function is never called on Android, but we need to provide an actual implementation
actual fun createDriver(): SQLiteDriver {
    error("createDriver should not be called on Android - Room uses built-in SQLite")
}
