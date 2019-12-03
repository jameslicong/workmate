package com.workmate.attendace.usecase.remote

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.model.JobInformation
import com.workmate.attendace.utilities.retrofit.HelpsterTechRestApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultJobInfoRemoteLoader

    @Inject
    internal constructor(private val api: HelpsterTechRestApi) : JobInfoRemoteLoader {

    override fun load(apiKey: ApiKey): Single<JobInformation> {
        return api.loadJobInformation(apiKey.key)
            .subscribeOn(Schedulers.io())
    }
}