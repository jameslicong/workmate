package com.workmate.attendance.utilities.retrofit

import dagger.Binds
import dagger.Module

@Module
abstract class RetrofitModule {

    @Binds
    internal abstract fun bindApiFactory(
        apiFactory: DefaultApiFactory
    ): ApiFactory
}