package com.workmate.attendance.usecase.local

import io.reactivex.Completable

interface AttendanceStateLocalDeleter {

    fun clearAll(): Completable
}