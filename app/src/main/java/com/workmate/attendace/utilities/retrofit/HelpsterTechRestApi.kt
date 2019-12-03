package com.workmate.attendace.utilities.retrofit

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.model.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface HelpsterTechRestApi {

    @POST("auth/login/")
    fun login(
        @Body user: User
    ): Single<ApiKey>
}