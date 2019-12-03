package com.workmate.attendace.utilities.framework

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class FrameworkModule {

    @Binds
    abstract fun bindMovieInfoViewModelFactory(
        factory: WorkmateAttendanceViewModelFactory): ViewModelProvider.Factory
}