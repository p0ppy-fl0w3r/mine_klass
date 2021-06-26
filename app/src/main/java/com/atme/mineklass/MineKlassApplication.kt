package com.atme.mineklass

import android.app.Application
import timber.log.Timber

class MineKlassApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}