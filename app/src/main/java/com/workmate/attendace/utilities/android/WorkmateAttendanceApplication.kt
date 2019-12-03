package com.workmate.attendace.utilities.android

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class WorkmateAttendanceApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerWorkmateAttendanceComponent.builder().create(this)
    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }
}