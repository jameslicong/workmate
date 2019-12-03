package com.workmate.attendace.utilities.preference

import dagger.Binds
import dagger.Module

@Module
abstract class SharedPrefUtilsModule {

    @Binds
    internal abstract fun bindSharedPrefUtils(
        sharedPrefUtils: SharedPreferencesCache
    ): Cache
}