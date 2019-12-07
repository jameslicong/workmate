package com.workmate.attendance.usecase.remote

import com.workmate.attendance.model.Attendance
import com.workmate.attendance.model.AttendanceLocation
import com.workmate.attendance.model.JobInformation
import com.workmate.attendance.model.Timesheet
import com.workmate.attendance.utilities.retrofit.ApiFactory
import com.workmate.attendance.utilities.retrofit.AttendanceRestApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DefaultAttendanceRemoteSaver

    @Inject
    internal constructor(private val apiFactory: ApiFactory) : AttendanceRemoteSaver {

    override fun clockIn(
        jobInfo: JobInformation,
        location: AttendanceLocation
    ): Single<Timesheet> {
        return apiFactory.createWithApiKeys(AttendanceRestApi::class.java)
            .flatMap {
                it.clockIn(
                    jobId = jobInfo.id,
                    attendanceLocation = location) }
            .doOnError { Throwable("You already clocked in") }
            .subscribeOn(Schedulers.io())
    }

    override fun clockOut(
        jobInfo: JobInformation,
        location: AttendanceLocation
    ): Single<Timesheet> {
        return apiFactory.createWithApiKeys(AttendanceRestApi::class.java)
            .flatMap {
                it.clockOut(
                    jobId = jobInfo.id,
                    attendanceLocation = location) }
            .flatMap { Single.just(it.timesheet) }
            .doOnError { Throwable("You already clocked out") }
            .subscribeOn(Schedulers.io())
    }
}