package com.workmate.attendace.usecase.local

import dagger.Binds
import dagger.Module

@Module
abstract class LocalUsecaseModule {

    @Binds
    internal abstract fun bindAttendanceStateLocalLoader(
        attendanceStateLocalLoader: DefaultAttendanceStateLocalLoader
    ): AttendanceStateLocalLoader

    @Binds
    internal abstract fun bindUserLocalLoader(
        userLocalLoader: DefaultUserLocalLoader
    ): UserLocalLoader

    @Binds
    internal abstract fun bindApiKeyLocalLoader(
        apiKeyLocalLoader: DefaultApiKeyLocalLoader
    ): ApiKeyLocalLoader

    @Binds
    internal abstract fun bindApiKeyLocalSaver(
        apiKeyLocalSaver: DefaultApiKeyLocalSaver
    ): ApiKeyLocalSaver
}