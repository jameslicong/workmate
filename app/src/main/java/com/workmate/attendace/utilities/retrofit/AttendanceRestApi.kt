package com.workmate.attendace.utilities.retrofit

import com.workmate.attendace.model.Attendance
import com.workmate.attendace.model.AttendanceLocation
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AttendanceRestApi {
    @POST("staff-requests/{job-id}/clock-in/")
    fun clockIn(
        @Path("job-id") jobId: String,
        @Body attendanceLocation: AttendanceLocation
    ): Single<Attendance>

    @POST("staff-requests/{job-id}/clock-out/")
    fun clockOut(
        @Path("job-id") jobId: String,
        @Body attendanceLocation: AttendanceLocation): Single<Attendance>
}