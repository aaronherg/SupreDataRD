package com.aarondeveloper.supredatard

import android.app.Application
import com.aarondeveloper.supredatard.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class SupreDataRDApp : Application() {

    companion object {
        lateinit var instance: SupreDataRDApp
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SupreDataRDApp)
        }
    }

}

