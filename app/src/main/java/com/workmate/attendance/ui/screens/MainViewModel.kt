package com.workmate.attendance.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.workmate.attendance.model.JobInformation
import com.workmate.attendance.usecase.LoginAuthenticator
import com.workmate.attendance.usecase.local.AttendanceStateLocalLoader
import com.workmate.attendance.usecase.remote.JobInfoRemoteLoader
import com.workmate.attendance.utilities.Constants.AttendanceState
import com.workmate.attendance.utilities.framework.BaseViewModel
import com.workmate.attendance.utilities.rx.RxSchedulerUtils
import timber.log.Timber
import javax.inject.Inject

class MainViewModel

    @Inject
    internal constructor(
        private val attendanceStateLocalLoader: AttendanceStateLocalLoader,
        private val loginAuthenticator: LoginAuthenticator,
        private val jobInfoRemoteLoader: JobInfoRemoteLoader,
        private val scheduler: RxSchedulerUtils
    ) : BaseViewModel() {

    private val jobInfoLiveData: MutableLiveData<JobInformation> = MutableLiveData()

    private val attendanceStateLiveData: MutableLiveData<AttendanceState> = MutableLiveData()



    fun onLoadJobInfo(): LiveData<JobInformation> {
        return jobInfoLiveData
    }

    fun onLoadAttendanceState(): LiveData<AttendanceState> {
        return attendanceStateLiveData
    }

    fun autoLogin() {
        addDisposable(
            loginAuthenticator.login()
                .compose(scheduler.forCompletable())
                .subscribe(
                    {
                        Timber.d("LOGIN SUCCESSFUL")
                        loadJobInformation()
                        loadAttendanceState()
                    },
                    {
                        it.printStackTrace()
                        Timber.e("LOGIN FAILED ${it.message}")
                    }
                )
        )
    }

    fun clockInOrOut() {
        // TODO clock in or out
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

    private fun loadAttendanceState() {
        addDisposable(
            attendanceStateLocalLoader.currentState()
                .compose(scheduler.forSingle())
                .subscribe(
                    {
                        if (it == AttendanceState.TO_CLOCK_IN) {
                            attendanceStateLiveData.postValue(it)
                        } else {
                            // TO LOAD Data
                        }
                    },
                    {

                    }
                )
        )
    }

    private fun loadAttendaceInfo(jobInfo: JobInformation) {
//        addDisposable(
//            attendanceRemoteSaver.clockIn(
//                jobInfo, AttendanceLocation(latitude = "-6.2446691", longitude = "106.8779625"))
//                .compose(scheduler.forSingle())
//                .subscribe(
//                    {
//                        Timber.d("Attendance $it")
//                    },
//                    {
//                        it.printStackTrace()
//                        Timber.e("Attendance Error ${it.message}")
//                    }
//                )
//        )
    }
}
