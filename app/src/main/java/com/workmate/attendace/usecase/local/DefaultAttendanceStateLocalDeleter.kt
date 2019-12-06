package com.workmate.attendace.usecase.local

import com.workmate.attendace.utilities.Constants.SharedPrefKeys
import com.workmate.attendace.utilities.preference.Cache
import io.reactivex.Completable
import javax.inject.Inject

class DefaultAttendanceStateLocalDeleter

    @Inject
    internal constructor(private val cache: Cache) : AttendanceStateLocalDeleter {

    override fun clearAll(): Completable {
        return Completable.fromCallable {
            cache.remove(SharedPrefKeys.CLOCK_IN)
            cache.remove(SharedPrefKeys.CLOCK_OUT)
        }
    }
}