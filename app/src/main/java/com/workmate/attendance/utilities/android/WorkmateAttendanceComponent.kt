package com.workmate.attendance.utilities.android

import com.workmate.attendance.ui.screens.ScreenModule
import com.workmate.attendance.usecase.UsecaseModule
import com.workmate.attendance.utilities.UtilitiesModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component( modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AndroidModule::class,
    ScreenModule::class,
    UsecaseModule::class,
    UtilitiesModule::class
])
@Singleton
interface WorkmateAttendanceComponent : AndroidInjector<WorkmateAttendanceApplication> {

    @Component.Builder
    abstract class WorkmateAttendanceComponentBuilder
        : AndroidInjector.Builder<WorkmateAttendanceApplication>()
}