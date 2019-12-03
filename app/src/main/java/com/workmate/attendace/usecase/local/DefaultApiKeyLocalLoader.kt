package com.workmate.attendace.usecase.local

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.utilities.Constants.SharedPrefKeys
import com.workmate.attendace.utilities.preference.Cache
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultApiKeyLocalLoader

    @Inject
    internal constructor(
        private val cache: Cache
    ) : ApiKeyLocalLoader {
    override fun load(): Single<ApiKey> {
        return Single.just(cache.getString(SharedPrefKeys.API_KEY))
            .flatMap {
                if (!it.isNullOrEmpty() && !it.isNullOrBlank()) {
                    Single.just(ApiKey(it))
                } else {
                    Single.error(Throwable("API KEY EMPTY"))
                }

            }
            .subscribeOn(Schedulers.io())
    }

}