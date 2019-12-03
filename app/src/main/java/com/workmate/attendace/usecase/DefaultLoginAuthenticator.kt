package com.workmate.attendace.usecase

import com.workmate.attendace.usecase.local.ApiKeyLocalLoader
import com.workmate.attendace.usecase.local.ApiKeyLocalSaver
import com.workmate.attendace.usecase.local.UserLocalLoader
import com.workmate.attendace.usecase.remote.UserLogin
import io.reactivex.Completable
import timber.log.Timber
import javax.inject.Inject

class DefaultLoginAuthenticator

    @Inject
    internal constructor(
        private val apiKeyLocalLoader: ApiKeyLocalLoader,
        private val apiKeyLocalSaver: ApiKeyLocalSaver,
        private val userLocalLoader: UserLocalLoader,
        private val userLogin: UserLogin
    ): LoginAuthenticator {

    override fun login(): Completable {
        return apiKeyLocalLoader.load()
            .flatMapCompletable { Completable.complete() }
            .onErrorResumeNext {
                loginDefaultUser()
            }
            .doOnError {
                Timber.e("FAILED TO LOAD API KEY ")
            }
            .doOnComplete {
                Timber.d("API KEY SUCCESSFULLY loaded")
            }
    }

    private fun loginDefaultUser(): Completable {
        Timber.i("LOGIN DEFAULT USER")
        return userLocalLoader.current()
            .flatMap { user -> userLogin.login(user) }
            .flatMapCompletable { apiKey -> apiKeyLocalSaver.save(apiKey) }
    }
}