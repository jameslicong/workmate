package com.workmate.attendace.usecase.local

import com.workmate.attendace.utilities.Constants.AttendanceState
import com.workmate.attendace.utilities.Constants.SharedPrefKeys
import com.workmate.attendace.utilities.preference.Cache
import io.reactivex.Single
import javax.inject.Inject

class DefaultAttendanceStateLocalLoader

    @Inject
    internal constructor(private val cache: Cache) : AttendanceStateLocalLoader {

    override fun currentState(): Single<AttendanceState> {
        return Single.create{ emitter ->
            val clockInValue = cache.getLong(SharedPrefKeys.CLOCK_IN)
            if (clockInValue != 0.toLong()) {
                emitter.onSuccess(AttendanceState.CLOCK_OUT)
            } else {
                emitter.onSuccess(AttendanceState.CLOCK_IN)
            }
        }
    }
}