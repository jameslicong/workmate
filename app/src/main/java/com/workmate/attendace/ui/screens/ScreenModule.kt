package com.workmate.attendace.ui.screens

import com.workmate.attendace.utilities.dagger.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreenModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity


    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

}