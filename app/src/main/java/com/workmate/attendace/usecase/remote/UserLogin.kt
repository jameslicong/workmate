package com.workmate.attendace.usecase.remote

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.model.User
import io.reactivex.Single

interface UserLogin {

    fun login(user: User): Single<ApiKey>
}