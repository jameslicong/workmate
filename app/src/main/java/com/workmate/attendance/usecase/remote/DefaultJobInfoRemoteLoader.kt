package com.workmate.attendance.usecase.remote

import com.workmate.attendance.model.JobInformation
import com.workmate.attendance.utilities.retrofit.ApiFactory
import com.workmate.attendance.utilities.retrofit.JobInfoRestApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultJobInfoRemoteLoader

    @Inject
    internal constructor(private val apiFactory: ApiFactory) : JobInfoRemoteLoader {

    override fun load(): Single<JobInformation> {
        return apiFactory.createWithApiKeys(JobInfoRestApi::class.java)
            .flatMap { it.loadJobInformation() }
            .subscribeOn(Schedulers.io())
    }
}