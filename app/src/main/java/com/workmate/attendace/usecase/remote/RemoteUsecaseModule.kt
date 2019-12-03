package com.workmate.attendace.usecase.remote

import dagger.Binds
import dagger.Module

@Module
abstract class RemoteUsecaseModule {

    @Binds
    internal abstract fun bindUserLogin(
        userLogin: DefaultUserLogin
    ): UserLogin
}