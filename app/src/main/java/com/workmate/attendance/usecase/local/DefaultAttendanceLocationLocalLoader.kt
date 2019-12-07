package com.workmate.attendance.usecase.local

import com.workmate.attendance.model.AttendanceLocation
import io.reactivex.Single
import javax.inject.Inject

class DefaultAttendanceLocationLocalLoader

    @Inject
    internal constructor() : AttendanceLocationLocalLoader {

    override fun current(): Single<AttendanceLocation> {
        return Single.just(
            AttendanceLocation(latitude = "-6.2446691", longitude = "106.8779625"))
    }
}