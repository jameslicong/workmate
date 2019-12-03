package com.workmate.attendace.usecase

import com.workmate.attendace.usecase.local.LocalUsecaseModule
import com.workmate.attendace.usecase.remote.RemoteUsecaseModule
import dagger.Module


@Module(includes = [
    LocalUsecaseModule::class,
    RemoteUsecaseModule::class
])
class UsecaseModule