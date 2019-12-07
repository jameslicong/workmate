package com.workmate.attendance.usecase.local

import com.workmate.attendance.utilities.Constants.SharedPrefKeys
import com.workmate.attendance.utilities.preference.Cache
import io.reactivex.Completable
import javax.inject.Inject

class DefaultAttendanceStateLocalSaver

    @Inject
    internal constructor(private val cache: Cache) : AttendanceStateLocalSaver {

    override fun toClockInState(timestamp: Long): Completable {
        return Completable.fromCallable { cache.setLong(SharedPrefKeys.CLOCK_IN, timestamp) }
    }

    override fun toClockOutState(timestamp: Long): Completable {
        return Completable.fromCallable { cache.setLong(SharedPrefKeys.CLOCK_OUT, timestamp) }
    }
}