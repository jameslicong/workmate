package com.workmate.attendace.ui.screens

import com.workmate.attendace.utilities.dagger.ActivityScope
import com.workmate.attendace.utilities.dagger.FragmentScope
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