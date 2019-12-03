package com.workmate.attendace.usecase.local

import dagger.Binds
import dagger.Module

@Module
abstract class LocalUsecaseModule {

    @Binds
    internal abstract fun bindUserLocalLoader(
        userLocalLoader: DefaultUserLocalLoader
    ): UserLocalLoader
}