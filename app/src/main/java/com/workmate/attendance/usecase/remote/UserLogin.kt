package com.workmate.attendance.usecase.remote

import com.workmate.attendance.model.ApiKey
import com.workmate.attendance.model.User
import io.reactivex.Single

interface UserLogin {

    fun login(user: User): Single<ApiKey>
}