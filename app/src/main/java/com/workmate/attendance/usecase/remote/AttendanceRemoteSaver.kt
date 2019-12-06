package com.workmate.attendance.usecase.remote

import com.workmate.attendance.model.Attendance
import com.workmate.attendance.model.AttendanceLocation
import com.workmate.attendance.model.JobInformation
import io.reactivex.Single

interface AttendanceRemoteSaver {

    fun clockIn(jobInfo: JobInformation, location: AttendanceLocation): Single<Attendance>

    fun clockOut(jobInfo: JobInformation, location: AttendanceLocation): Single<Attendance>
}