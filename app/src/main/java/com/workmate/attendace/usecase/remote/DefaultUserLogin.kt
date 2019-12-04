package com.workmate.attendace.usecase.remote

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.model.User
import com.workmate.attendace.utilities.retrofit.ApiFactory
import com.workmate.attendace.utilities.retrofit.HelpsterTechRestApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
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