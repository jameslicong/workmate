package com.workmate.attendance.usecase

import com.workmate.attendance.usecase.local.LocalUsecaseModule
import com.workmate.attendance.usecase.remote.RemoteUsecaseModule
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

    @Binds
    abstract fun bindInternet(
        internet: DefaultInternet
    ): Internet
}