package com.workmate.attendace.usecase.local

import com.workmate.attendace.model.ApiKey
import io.reactivex.Completable

interface ApiKeyLocalSaver {

    fun save(apiKey: ApiKey): Completable
}