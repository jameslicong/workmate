package com.workmate.attendace.usecase.remote

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.model.JobInformation
import com.workmate.attendace.utilities.retrofit.ApiFactory
import com.workmate.attendace.utilities.retrofit.HelpsterTechRestApi
import com.workmate.attendace.utilities.retrofit.JobInfoRestApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultJobInfoRemoteLoader

    @Inject
    internal constructor(private val apiFactory: ApiFactory) : JobInfoRemoteLoader {

    override fun load(apiKey: ApiKey): Single<JobInformation> {
        return apiFactory.createWithApiKeys(JobInfoRestApi::class.java)
            .flatMap { it.loadJobInformation() }
            .subscribeOn(Schedulers.io())
    }
}