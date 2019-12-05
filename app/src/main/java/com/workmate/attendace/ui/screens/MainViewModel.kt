package com.workmate.attendace.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.workmate.attendace.model.ApiKey
import com.workmate.attendace.model.AttendanceLocation
import com.workmate.attendace.model.JobInformation
import com.workmate.attendace.usecase.LoginAuthenticator
import com.workmate.attendace.usecase.remote.AttendanceRemoteSaver
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
        private val attendanceRemoteSaver: AttendanceRemoteSaver,
        private val scheduler: RxSchedulerUtils
    ) : BaseViewModel() {

    private val jobInfoLiveData: MutableLiveData<JobInformation> = MutableLiveData()

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

    fun onLoadJobInfo(): LiveData<JobInformation> {
        return jobInfoLiveData
    }

    private fun loadJobInformation() {
        addDisposable(
            jobInfoRemoteLoader.load()
                .compose(scheduler.forSingle())
                .subscribe(
                    {
                        Timber.d("JOB INFO $it")
                        jobInfoLiveData.postValue(it)
                    },
                    {
                        it.printStackTrace()
                        Timber.e("FAILED TO LOAD JOB INFO ${it.message}")
                    }
                )
        )
    }

    private fun tryClockIn(jobInfo: JobInformation) {
        addDisposable(
            attendanceRemoteSaver.clockIn(
                jobInfo, AttendanceLocation(latitude = "-6.2446691", longitude = "106.8779625"))
                .compose(scheduler.forSingle())
                .subscribe(
                    {
                        Timber.d("Attendance $it")
                    },
                    {
                        it.printStackTrace()
                        Timber.e("Attendance Error ${it.message}")
                    }
                )
        )
    }
}
