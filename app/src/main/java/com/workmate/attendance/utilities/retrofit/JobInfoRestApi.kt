package com.workmate.attendance.utilities.retrofit

import com.workmate.attendance.model.JobInformation
import io.reactivex.Single
import retrofit2.http.GET

interface JobInfoRestApi {

    @GET("staff-requests/26074/")
    fun loadJobInformation(): Single<JobInformation>
}