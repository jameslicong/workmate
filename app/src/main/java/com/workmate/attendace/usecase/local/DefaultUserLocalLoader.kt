package com.workmate.attendace.usecase.local

import com.workmate.attendace.BuildConfig
import com.workmate.attendace.model.User
import io.reactivex.Single
import javax.inject.Inject

class DefaultUserLocalLoader

    @Inject
    internal constructor(): UserLocalLoader {
    
    override fun current(): Single<User> {
        return Single.just(User(BuildConfig.DEFAULT_USERNAME, BuildConfig.DEFAULT_PASSWORD))
    }

}