package com.workmate.attendance.usecase.local

import com.workmate.attendance.model.AttendanceLocation
import io.reactivex.Single

interface AttendanceLocationLocalLoader {

    fun current(): Single<AttendanceLocation>
}

