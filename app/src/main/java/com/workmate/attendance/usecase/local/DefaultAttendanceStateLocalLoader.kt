package com.workmate.attendance.usecase.local

import com.workmate.attendance.utilities.Constants.AttendanceState
import com.workmate.attendance.utilities.Constants.SharedPrefKeys
import com.workmate.attendance.utilities.preference.Cache
import io.reactivex.Single
import javax.inject.Inject

class DefaultAttendanceStateLocalLoader

    @Inject
    internal constructor(private val cache: Cache) : AttendanceStateLocalLoader {

    override fun currentState(): Single<AttendanceState> {
        return Single.create{ emitter ->
            val clockInValue = cache.getLong(SharedPrefKeys.CLOCK_IN)
            if (clockInValue != 0.toLong()) {
                emitter.onSuccess(AttendanceState.TO_CLOCK_OUT)
            } else {
                emitter.onSuccess(AttendanceState.TO_CLOCK_IN)
            }
        }
    }

    override fun byKey(attendaceState: AttendanceState): Single<Long> {
        return when (attendaceState) {
            AttendanceState.TO_CLOCK_IN -> Single.just(cache.getLong(SharedPrefKeys.CLOCK_IN))
            else -> Single.just(cache.getLong(SharedPrefKeys.CLOCK_OUT))
        }
    }

}