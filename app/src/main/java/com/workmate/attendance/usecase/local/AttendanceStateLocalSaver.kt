package com.workmate.attendance.usecase.local

import io.reactivex.Completable

interface AttendanceStateLocalSaver  {

    fun toClockInState(timestamp: Long): Completable

    fun toClockOutState(timestamp: Long): Completable
}