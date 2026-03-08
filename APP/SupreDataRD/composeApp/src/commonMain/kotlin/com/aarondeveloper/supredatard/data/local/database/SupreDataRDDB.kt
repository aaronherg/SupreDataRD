package com.aarondeveloper.supredatard.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.aarondeveloper.supredatard.data.local.dao.ConfiguracionDao
import com.aarondeveloper.supredatard.data.local.entities.ConfiguracionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val DATABASE_NAME = "supredatard.db"

@Database(
    entities = [ConfiguracionEntity::class],
    version = 2,
    exportSchema = false
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class SupreDataRDDB : RoomDatabase() {
    abstract fun getConfiguracionDao(): ConfiguracionDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<SupreDataRDDB> {
    override fun initialize(): SupreDataRDDB
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<SupreDataRDDB>
): SupreDataRDDB {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}