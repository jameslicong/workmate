package com.workmate.attendace.usecase.remote

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.model.User
import com.workmate.attendace.utilities.retrofit.HelpsterTechRestApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DefaultUserLogin

    @Inject
    internal constructor(private val api: HelpsterTechRestApi) : UserLogin {

    override fun login(user: User): Single<ApiKey> {
        return api.login(user)
            .subscribeOn(Schedulers.io())
    }
}