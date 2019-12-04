package com.workmate.attendace.usecase.remote

import com.workmate.attendace.model.Attendance
import com.workmate.attendace.model.AttendanceLocation
import com.workmate.attendace.model.JobInformation
import io.reactivex.Single

interface AttendanceRemoteSaver {

    fun clockIn(jobInfo: JobInformation, location: AttendanceLocation): Single<Attendance>

    fun clockOut(jobInfo: JobInformation, location: AttendanceLocation): Single<Attendance>
}