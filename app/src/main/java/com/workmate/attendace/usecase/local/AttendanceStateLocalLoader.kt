package com.workmate.attendace.usecase.local

import com.workmate.attendace.utilities.Constants.AttendanceState
import io.reactivex.Single

interface AttendanceStateLocalLoader {

    fun currentState(): Single<AttendanceState>
}