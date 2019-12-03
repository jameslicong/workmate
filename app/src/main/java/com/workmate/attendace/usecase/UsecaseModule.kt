package com.workmate.attendace.usecase

import com.workmate.attendace.usecase.local.LocalUsecaseModule
import com.workmate.attendace.usecase.remote.RemoteUsecaseModule
import dagger.Binds
import dagger.Module


@Module(includes = [
    LocalUsecaseModule::class,
    RemoteUsecaseModule::class
])
abstract class UsecaseModule {

    @Binds
    abstract fun bindLoginAuthenticator(
        loginAuthenticator: DefaultLoginAuthenticator
    ): LoginAuthenticator
}