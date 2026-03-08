package com.aarondeveloper.supredatard.di

import com.aarondeveloper.supredatard.data.local.database.getDatabaseBuilder
import com.aarondeveloper.supredatard.data.local.database.getRoomDatabase
import org.koin.dsl.module

actual val nativeModule = module {
    single {
        val builder = getDatabaseBuilder()
        getRoomDatabase(builder).getConfiguracionDao()
    }
}