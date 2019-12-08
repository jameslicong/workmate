package com.workmate.attendance.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.workmate.attendance.model.JobInformation
import com.workmate.attendance.usecase.Internet
import com.workmate.attendance.usecase.LoginAuthenticator
import com.workmate.attendance.usecase.local.AttendanceStateLocalDeleter
import com.workmate.attendance.usecase.local.AttendanceStateLocalLoader
import com.workmate.attendance.usecase.remote.JobInfoRemoteLoader
import com.workmate.attendance.utilities.Constants.AttendanceState
import com.workmate.attendance.utilities.framework.BaseViewModel
import com.workmate.attendance.utilities.rx.RxSchedulerUtils
import com.workmate.attendance.utilities.toHoursAndMinuteFormat
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class MainViewModel

    @Inject
    internal constructor(
        private val attendanceStateLocalDeleter: AttendanceStateLocalDeleter,
        private val attendanceStateLocalLoader: AttendanceStateLocalLoader,
        private val internet: Internet,
        private val loginAuthenticator: LoginAuthenticator,
        private val jobInfoRemoteLoader: JobInfoRemoteLoader,
        private val scheduler: RxSchedulerUtils
    ) : BaseViewModel() {

    private val jobInfoLiveData: MutableLiveData<JobInformation> = MutableLiveData()

    private val attendanceStateLiveData: MutableLiveData<AttendanceState> = MutableLiveData()

    private val timeInValue: MutableLiveData<String> = MutableLiveData()

    private val timeOutValue: MutableLiveData<String> = MutableLiveData()

    private val throwableExceptionLiveData: MutableLiveData<Throwable> = MutableLiveData()

    fun onLoadJobInfo(): LiveData<JobInformation> {
        return jobInfoLiveData
    }

    fun onLoadAttendanceState(): LiveData<AttendanceState> {
        return attendanceStateLiveData
    }

    fun onLoadTimeInValue(): LiveData<String> {
        return timeInValue
    }

    fun onLoadTimeOutValue(): LiveData<String> {
        return timeOutValue
    }

    fun onThrowableExceptionHappen(): LiveData<Throwable> {
        return throwableExceptionLiveData
    }

    fun autoLogin() {
        addDisposable(
            internet.isConnected()
                .andThen(loginAuthenticator.login())
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
                        throwableExceptionLiveData.postValue(it)
                    }
                )
        )
    }

    fun loadAttendanceTimeInDetails() {
        addDisposable(
            internet.isConnected()
                .andThen(attendanceStateLocalLoader.byKey(AttendanceState.TO_CLOCK_IN))
                .flatMap {
                    if (it.toInt() != 0) { Single.just(it.toHoursAndMinuteFormat()) }
                    else { Single.error(Throwable("Clock int empty "))}
                }
                .compose(scheduler.forSingle())
                .subscribe(
                    { timeInValue.postValue(it) },
                    {
                        Timber.e("${it.message}")
                    }
                )
        )

        addDisposable(
            attendanceStateLocalLoader.byKey(AttendanceState.TO_CLOCK_OUT)
                .flatMap {
                    if (it.toInt() != 0) { Single.just(it.toHoursAndMinuteFormat()) }
                    else { Single.error(Throwable("Clock out empty "))}
                }
                .compose(scheduler.forSingle())
                .subscribe(
                    { timeOutValue.postValue(it) },
                    { Timber.e("${it.message}") }
                )
        )
    }

    fun deleteAttendanceState() {
        addDisposable(
            attendanceStateLocalDeleter.clearAll()
                .compose(scheduler.forCompletable())
                .subscribe()
        )
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
                        throwableExceptionLiveData.postValue(it)
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
                        attendanceStateLiveData.postValue(it)
                    },
                    {
                        it.printStackTrace()
                        Timber.e("Failed to load attendance state ${it.message}")
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
