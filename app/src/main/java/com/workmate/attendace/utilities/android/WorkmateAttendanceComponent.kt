package com.workmate.attendace.utilities.android

import com.workmate.attendace.ui.screens.ScreenModule
import com.workmate.attendace.usecase.UsecaseModule
import com.workmate.attendace.utilities.UtilitiesModule
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