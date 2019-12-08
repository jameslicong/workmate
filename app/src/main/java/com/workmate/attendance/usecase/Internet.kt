package com.workmate.attendance.usecase

import io.reactivex.Completable

interface Internet {

    fun isConnected(): Completable
}