package com.example.physics_lab

import android.app.Application
import com.example.physics_lab.BuildConfig.DEBUG
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}