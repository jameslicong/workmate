package com.workmate.attendace.usecase.remote

import com.workmate.attendace.model.JobInformation
import io.reactivex.Single

interface JobInfoRemoteLoader {

    fun load(): Single<JobInformation>
}