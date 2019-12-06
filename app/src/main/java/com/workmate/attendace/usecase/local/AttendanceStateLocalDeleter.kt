package com.workmate.attendace.usecase.local

import io.reactivex.Completable

interface AttendanceStateLocalDeleter {

    fun clearAll(): Completable
}