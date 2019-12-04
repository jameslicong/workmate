package com.workmate.attendace.usecase.remote

import dagger.Binds
import dagger.Module

@Module
abstract class RemoteUsecaseModule {

    @Binds
    internal abstract fun bindAttendanceRemoteSaver(
        attendanceRemoteSaver: DefaultAttendanceRemoteSaver
    ): AttendanceRemoteSaver

    @Binds
    internal abstract fun bindJobInfoRemoteLoader(
        jobInfoRemoteLoader: DefaultJobInfoRemoteLoader
    ): JobInfoRemoteLoader

    @Binds
    internal abstract fun bindUserLogin(
        userLogin: DefaultUserLogin
    ): UserLogin
}