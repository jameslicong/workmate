package com.workmate.attendance.ui.screens

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import butterknife.*
import com.workmate.attendance.R
import com.workmate.attendance.model.JobInformation
import com.workmate.attendance.ui.screens.TimeEntryViewModel.State
import com.workmate.attendance.utilities.Constants.AttendanceState
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class TimeEntryFragment : DaggerFragment() {

    companion object {

        private const val EXTRA_ATTENDANCE_TYPE = "attendanceType"
        private const val EXTRA_JOB_INFO = "jobInformation"

        fun createWithJobInfoAndAttendanceState(
            jobInfo: JobInformation,
            attendanceState: AttendanceState): TimeEntryFragment {
            val timeEntryFragment = TimeEntryFragment()
            val args = Bundle()
            args.putInt(EXTRA_ATTENDANCE_TYPE, attendanceState.value)
            args.putSerializable(EXTRA_JOB_INFO, jobInfo)

            timeEntryFragment.arguments = args
            return timeEntryFragment
        }
    }

    @Inject
    lateinit var viewModel: TimeEntryViewModel

    @BindView(R.id.title)
    lateinit var title: TextView

    @BindView(R.id.cancel_button)
    lateinit var cancelBtn: TextView

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @BindString(R.string.cancel)
    lateinit var cancelText: String

    @BindString(R.string.clocking_in)
    lateinit var clockingInText: String

    @BindString(R.string.clocking_out)
    lateinit var clockingOutText: String

    @BindDrawable(R.drawable.custom_progress_bar)
    lateinit var progressBarCustomDrawable: Drawable

    private var currentAttendanceState : AttendanceState = AttendanceState.TO_CLOCK_IN

    @OnClick(R.id.cancel_button)
    internal fun onClickCancel() {
        viewModel.onCancelTimer()
        fragmentManager?.popBackStack()
    }

    @OnClick(R.id.clock_in_btn)
    internal fun onClickClockIn() {
        Timber.d("onClickClockIn")
        doSaveAttendance(AttendanceState.TO_CLOCK_IN)
    }

    @OnClick(R.id.clock_out_btn)
    internal fun onClickClockOut() {
        Timber.d("onClickClockOut")
        doSaveAttendance(AttendanceState.TO_CLOCK_OUT)
    }

    private var disposable: Disposable? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        var view =
            inflater.inflate(R.layout.time_entry_fragment, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.progress = 20
        val underlinedText = SpannableString(cancelText)
        underlinedText.setSpan(UnderlineSpan(), 0, underlinedText.length, 0)
        cancelBtn.text = underlinedText

        if (arguments!!.containsKey(EXTRA_ATTENDANCE_TYPE)) {
            if (arguments!!.getInt(EXTRA_ATTENDANCE_TYPE) == AttendanceState.TO_CLOCK_OUT.value) {
                currentAttendanceState = AttendanceState.TO_CLOCK_OUT
            }
        }

        val titleText = when(currentAttendanceState) {
            AttendanceState.TO_CLOCK_IN -> clockingInText
            else  -> clockingOutText
        }
        title.text = titleText
        progressBar.progress = 0


        viewModel.onStartTimer()

        viewModel.onTimerProgress()
            .observe( this, Observer {
                progressBar.progress = it
            })

        viewModel.stateOnChange()
            .observe(this, Observer { state ->
                when (state) {
                    State.COUNTDOWN_TIMER_STARTED -> {
                        cancelBtn.isClickable = true
                        cancelBtn.isFocusable = true
                    }
                    State.COUNTDOWN_TIMER_ENDED -> {
                        cancelBtn.isClickable = false
                        cancelBtn.isFocusable = false
                        doSaveAttendance()
                    }
                    State.TIME_ENTRY_SAVING_SUCCESSFUL -> {
                        Timber.d("POPBACKSTACK TIME_ENTRY_SAVING_SUCCESSFUL()")
                        fragmentManager?.popBackStack()
                    }
                    State.TIME_ENTRY_SAVING_FAILED -> {

                        // TODO DO POP UP
                        //fragmentManager?.popBackStack()
                    }
                }
            })
    }


    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

    private fun doSaveAttendance() {
        if (arguments!!.containsKey(EXTRA_JOB_INFO)) {
            var jobInfo =
                arguments!!.getSerializable(EXTRA_JOB_INFO) as JobInformation
            viewModel.onSaveAttendance(jobInfo, currentAttendanceState)
        }
    }

    private fun doSaveAttendance(state: AttendanceState) {
        if (arguments!!.containsKey(EXTRA_JOB_INFO)) {
            var jobInfo =
                arguments!!.getSerializable(EXTRA_JOB_INFO) as JobInformation
            viewModel.onSaveAttendance(jobInfo, state)
        }
    }

//    private fun onStartCountDown() {
//        progressBar.progress = 0
//        disposable = Observable.interval(1, TimeUnit.SECONDS)
//            .take(10)
//            .map { counter -> counter + 1}
//            .compose(scheduler.forObservable())
//            .doOnNext {
//                Timber.d("Counter $it")
//                progressBar.progress = it.toInt() * 10
//            }
//            .subscribe()
//    }
}