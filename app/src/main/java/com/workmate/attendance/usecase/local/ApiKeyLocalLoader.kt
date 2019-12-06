package com.workmate.attendance.usecase.local

import com.workmate.attendance.model.ApiKey
import io.reactivex.Single

interface ApiKeyLocalLoader {

    fun load(): Single<ApiKey>
}