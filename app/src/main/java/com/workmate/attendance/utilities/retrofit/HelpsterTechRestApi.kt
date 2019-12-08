package com.workmate.attendance.utilities.retrofit

import com.workmate.attendance.model.ApiKey
import com.workmate.attendance.model.User
import io.reactivex.Single
import retrofit2.http.*

interface HelpsterTechRestApi {

    @POST("auth/login/")
    fun login(
        @Body user: User
    ): Single<ApiKey>
}