package com.workmate.attendace.usecase.local

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.utilities.Constants.SharedPrefKeys
import com.workmate.attendace.utilities.preference.Cache
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultApiKeyLocalSaver

    @Inject
    internal constructor(
        private val cache: Cache
    ): ApiKeyLocalSaver {

    override fun save(apiKey: ApiKey): Completable {
        return Completable.fromAction {
            cache.setString(SharedPrefKeys.API_KEY, apiKey.key)
        }.subscribeOn(Schedulers.io())
    }
}