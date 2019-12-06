package com.workmate.attendance.ui.screens

import com.workmate.attendance.utilities.dagger.ActivityScope
import com.workmate.attendance.utilities.dagger.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreenModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity


    @FragmentScope
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

}