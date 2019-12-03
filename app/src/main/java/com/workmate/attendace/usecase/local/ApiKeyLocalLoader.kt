package com.workmate.attendace.usecase.local

import com.workmate.attendace.model.ApiKey
import io.reactivex.Single

interface ApiKeyLocalLoader {

    fun load(): Single<ApiKey>
}