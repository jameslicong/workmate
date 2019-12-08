package com.workmate.attendance.usecase

import io.reactivex.Completable

interface LoginAuthenticator {

    fun login(): Completable
}