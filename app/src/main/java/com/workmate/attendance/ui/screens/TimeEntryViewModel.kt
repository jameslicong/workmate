package com.workmate.attendance.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.workmate.attendance.model.AttendanceLocation
import com.workmate.attendance.model.JobInformation
import com.workmate.attendance.usecase.local.AttendanceLocationLocalLoader
import com.workmate.attendance.usecase.local.AttendanceStateLocalSaver
import com.workmate.attendance.usecase.remote.AttendanceRemoteSaver
import com.workmate.attendance.utilities.Constants.AttendanceState
import com.workmate.attendance.utilities.framework.BaseViewModel
import com.workmate.attendance.utilities.preference.Cache
import com.workmate.attendance.utilities.rx.RxSchedulerUtils
import com.workmate.attendance.utilities.toTimestampLongValue
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimeEntryViewModel

    @Inject
    internal constructor(
        private val attendanceLocationLocalLoader: AttendanceLocationLocalLoader,
        private val attendanceStateLocalSaver: AttendanceStateLocalSaver,
        private val attendanceRemoteSaver: AttendanceRemoteSaver,
        private val cache: Cache,
        private val scheduler: RxSchedulerUtils
    ) : BaseViewModel() {

    enum class State {
        COUNTDOWN_TIMER_STARTED,
        COUNTDOWN_TIMER_ENDED,
        TIME_ENTRY_SAVING_STARTED,
        TIME_ENTRY_SAVING_SUCCESSFUL,
        TIME_ENTRY_SAVING_FAILED
    }

    private var disposable: Disposable? = null

    private val timerLiveData: MutableLiveData<Int> = MutableLiveData()

    private val stateLiveData: MutableLiveData<State> = MutableLiveData()


    fun onTimerProgress(): LiveData<Int> {
        return timerLiveData
    }

    fun stateOnChange(): LiveData<State> {
        return stateLiveData
    }

    fun onStartTimer() {
        disposable = Observable.interval(1, TimeUnit.SECONDS)
            .take(10)
            .map { counter -> counter + 1}
            .compose(scheduler.forObservable())
            .doOnNext {
                timerLiveData.postValue(it.toInt() * 10)
            }
            .doOnComplete {
                stateLiveData.postValue(State.COUNTDOWN_TIMER_ENDED)
                disposable?.dispose()
            }
            .doOnSubscribe {
                stateLiveData.postValue(State.COUNTDOWN_TIMER_STARTED)
            }
            .subscribe()

    }

    fun onCancelTimer() {
        disposable?.dispose()
    }

    fun onSaveAttendance(jobInfo: JobInformation,
                         attendanceState: AttendanceState) {
        stateLiveData.postValue(State.TIME_ENTRY_SAVING_STARTED)

        Timber.d("onSaveAttendance $attendanceState")

        addDisposable(
            attendanceLocationLocalLoader.current()
                .compose(scheduler.forSingle())
                .subscribe(
                    { attendanceLocation ->
                        when (attendanceState) {
                            AttendanceState.TO_CLOCK_IN ->
                                saveAttendanceClockingIn(jobInfo, attendanceLocation)
                            AttendanceState.TO_CLOCK_OUT ->
                                saveAttendanceClockingOut(jobInfo, attendanceLocation)
                        }
                    },{
                        it.printStackTrace()
                        Timber.e("Location Not Found ${it.message}")
                    }
                )
        )
    }

    private fun saveAttendanceClockingIn(
        jobInfo: JobInformation,
        attendanceLocation: AttendanceLocation) {

        addDisposable(
            attendanceRemoteSaver.clockIn(jobInfo, attendanceLocation)
                .flatMapCompletable { attendance ->
                    Timber.d("$attendance")
                    Timber.d("Time: ${attendance.clock_in_time.toTimestampLongValue()}")
                    attendanceStateLocalSaver
                        .toClockInState(attendance.clock_in_time.toTimestampLongValue()) }
                .compose(scheduler.forCompletable())
                .subscribe(
                    { successfulTimeEntrySavingPostValueWithDelay() },
                    { stateLiveData.postValue(State.TIME_ENTRY_SAVING_FAILED) }
                )
        )
    }

    private fun saveAttendanceClockingOut(
        jobInfo: JobInformation,
        attendanceLocation: AttendanceLocation) {

        addDisposable(
            attendanceRemoteSaver.clockOut(jobInfo, attendanceLocation)
                .flatMapCompletable { attendance ->
                    Timber.d("$attendance")
                    Timber.d("Time: ${attendance.clock_out_time?.toTimestampLongValue()}")
                    attendanceStateLocalSaver
                        .toClockOutState(attendance.clock_out_time!!.toTimestampLongValue()) }
                .compose(scheduler.forCompletable())
                .subscribe(
                    { successfulTimeEntrySavingPostValueWithDelay() },
                    { stateLiveData.postValue(State.TIME_ENTRY_SAVING_FAILED) }
                )
        )
    }

    private fun successfulTimeEntrySavingPostValueWithDelay() {
        Timber.d("successfulTimeEntrySavingPostValueWithDelay")
        addDisposable(
            Single.timer(2, TimeUnit.SECONDS)
                .compose(scheduler.forSingle())
                .subscribe(
                    {
                        Timber.d("SUCCESSFUL successfulTimeEntrySavingPostValueWithDelay()")
                        stateLiveData.postValue(State.TIME_ENTRY_SAVING_SUCCESSFUL)
                    },
                    {
                        it.printStackTrace()
                        Timber.e("saving failed ${it.message}")
                    }
                )
        )
//        addDisposable(
//            Observable.interval(1, TimeUnit.SECONDS).take(5)
//                .compose(scheduler.forObservable())
//                .doOnComplete { stateLiveData.postValue(State.TIME_ENTRY_SAVING_SUCCESSFUL) }
//                .subscribe()
//        )
    }
}