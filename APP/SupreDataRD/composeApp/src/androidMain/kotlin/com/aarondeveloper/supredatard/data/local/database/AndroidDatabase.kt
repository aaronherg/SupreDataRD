package com.aarondeveloper.supredatard.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver


fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<SupreDataRDDB> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<SupreDataRDDB>(
        context = appContext,
        name = dbFile.absolutePath
    )
}