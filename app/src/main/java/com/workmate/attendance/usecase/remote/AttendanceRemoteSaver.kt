package com.workmate.attendance.usecase.remote

import com.workmate.attendance.model.AttendanceLocation
import com.workmate.attendance.model.JobInformation
import com.workmate.attendance.model.Timesheet
import io.reactivex.Single

interface AttendanceRemoteSaver {

    fun clockIn(jobInfo: JobInformation, location: AttendanceLocation): Single<Timesheet>

    fun clockOut(jobInfo: JobInformation, location: AttendanceLocation): Single<Timesheet>
}