package com.workmate.attendace.ui.screens

import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.usecase.LoginAuthenticator
import com.workmate.attendace.usecase.remote.JobInfoRemoteLoader
import com.workmate.attendace.utilities.framework.BaseViewModel
import com.workmate.attendace.utilities.rx.RxSchedulerUtils
import timber.log.Timber
import javax.inject.Inject

class MainViewModel

    @Inject
    internal constructor(
        private val loginAuthenticator: LoginAuthenticator,
        private val jobInfoRemoteLoader: JobInfoRemoteLoader,
        private val scheduler: RxSchedulerUtils
    ) : BaseViewModel() {
    // TODO: Implement the ViewModel

    fun autoLogin() {

        addDisposable(
            loginAuthenticator.login()
                .compose(scheduler.forCompletable())
                .subscribe(
                    {
                        Timber.d("LOGIN SUCCESSFUL")
                        loadJobInformation()
                    },
                    {
                        it.printStackTrace()
                        Timber.e("LOGIN FAILED ${it.message}")
                    }
                )
        )
    }

    private fun loadJobInformation() {
        addDisposable(
            jobInfoRemoteLoader.load(ApiKey("e945ae028e2355e123cfdf1b4fbb81ad4e5b2ebc"))
                .compose(scheduler.forSingle())
                .subscribe(
                    {
                        Timber.d("JOB INFO $it")
                    },
                    {
                        it.printStackTrace()
                        Timber.e("FAILED TO LOAD JOB INFO ${it.message}")
                    }
                )
        )
    }
}
