package com.workmate.attendance.usecase.local

import com.workmate.attendance.model.ApiKey
import io.reactivex.Completable

interface ApiKeyLocalSaver {

    fun save(apiKey: ApiKey): Completable
}