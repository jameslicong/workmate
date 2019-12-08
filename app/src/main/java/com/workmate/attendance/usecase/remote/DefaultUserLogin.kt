package com.workmate.attendance.usecase.remote

import com.workmate.attendance.model.ApiKey
import com.workmate.attendance.model.User
import com.workmate.attendance.utilities.retrofit.ApiFactory
import com.workmate.attendance.utilities.retrofit.HelpsterTechRestApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultUserLogin

    @Inject
    internal constructor(private val apiFactory: ApiFactory) : UserLogin {

    override fun login(user: User): Single<ApiKey> {
        return apiFactory.create(HelpsterTechRestApi::class.java)
            .flatMap { it.login(user) }
            .subscribeOn(Schedulers.io())
    }
}