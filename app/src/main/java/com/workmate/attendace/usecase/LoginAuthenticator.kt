package com.workmate.attendace.usecase

import io.reactivex.Completable

interface LoginAuthenticator {

    fun login(): Completable
}