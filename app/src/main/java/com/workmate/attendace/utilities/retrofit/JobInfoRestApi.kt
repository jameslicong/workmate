package com.workmate.attendace.utilities.retrofit

import com.workmate.attendace.model.JobInformation
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface JobInfoRestApi {

    @GET("staff-requests/26074/")
    fun loadJobInformation(): Single<JobInformation>
}