package com.workmate.attendance.usecase.local

import com.workmate.attendance.utilities.Constants.AttendanceState
import io.reactivex.Single

interface AttendanceStateLocalLoader {

    fun currentState(): Single<AttendanceState>

    fun byKey(attendaceState: AttendanceState): Single<Long>
}