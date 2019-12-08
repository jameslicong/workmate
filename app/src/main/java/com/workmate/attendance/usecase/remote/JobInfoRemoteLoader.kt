package com.workmate.attendance.usecase.remote

import com.workmate.attendance.model.JobInformation
import io.reactivex.Single

interface JobInfoRemoteLoader {

    fun load(): Single<JobInformation>
}