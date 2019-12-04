package com.workmate.attendace.usecase.remote

import com.workmate.attendace.model.Attendance
import com.workmate.attendace.model.AttendanceLocation
import com.workmate.attendace.model.JobInformation
import com.workmate.attendace.utilities.retrofit.ApiFactory
import com.workmate.attendace.utilities.retrofit.AttendanceRestApi
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
    ): Single<Attendance> {
        return apiFactory.createWithApiKeys(AttendanceRestApi::class.java)
            .flatMap {
                it.clockIn(
                    jobId = jobInfo.id,
                    attendanceLocation = location) }
            .doOnError { Throwable("You already clocked out") }
            .subscribeOn(Schedulers.io())
    }

    override fun clockOut(
        jobInfo: JobInformation,
        location: AttendanceLocation
    ): Single<Attendance> {
        return apiFactory.createWithApiKeys(AttendanceRestApi::class.java)
            .flatMap {
                it.clockOut(
                    jobId = jobInfo.id,
                    attendanceLocation = AttendanceLocation(
                        latitude = "-6.2446691",
                        longitude = "106.8779625"
                    )) }
            .doOnError { Throwable("You already clocked out") }
            .subscribeOn(Schedulers.io())
    }
}