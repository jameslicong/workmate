package com.workmate.attendance.utilities.android

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule {

    @Singleton
    @Provides
    fun provideContext(application: WorkmateAttendanceApplication): Context {
        return application.applicationContext
    }
}