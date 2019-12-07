package com.workmate.attendance.utilities.retrofit

import com.workmate.attendance.model.Attendance
import com.workmate.attendance.model.AttendanceLocation
import com.workmate.attendance.model.Timesheet
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AttendanceRestApi {
    @POST("staff-requests/{job-id}/clock-in/")
    fun clockIn(
        @Path("job-id") jobId: String,
        @Body attendanceLocation: AttendanceLocation): Single<Timesheet>

    @POST("staff-requests/{job-id}/clock-out/")
    fun clockOut(
        @Path("job-id") jobId: String,
        @Body attendanceLocation: AttendanceLocation): Single<Attendance>
}