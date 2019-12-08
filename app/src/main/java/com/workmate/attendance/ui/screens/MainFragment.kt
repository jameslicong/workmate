package com.workmate.attendance.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import android.text.style.UnderlineSpan
import android.text.SpannableString
import com.google.android.material.snackbar.Snackbar
import com.workmate.attendance.R
import com.workmate.attendance.model.JobInformation
import com.workmate.attendance.utilities.Constants.AttendanceState


class MainFragment : DaggerFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    @BindView(R.id.job_info_container)
    lateinit var jobInfoContainer: ConstraintLayout

    @BindView(R.id.job_name)
    lateinit var jobName: TextView

    @BindView(R.id.wage_value)
    lateinit var wageValue: TextView

    @BindView(R.id.company_name)
    lateinit var companyName: TextView

    @BindView(R.id.wage_type)
    lateinit var wageType: TextView

    @BindView(R.id.company_address)
    lateinit var companyAddress: TextView

    @BindView(R.id.manager_position)
    lateinit var managerPosition: TextView

    @BindView(R.id.manager_name)
    lateinit var managerName: TextView

    @BindView(R.id.manager_contact_number)
    lateinit var managerContactNumber: TextView

    @BindView(R.id.contact_number_value)
    lateinit var managerContactNumberValue: TextView

    @BindView(R.id.clock_in_value)
    lateinit var clockInValue: TextView

    @BindView(R.id.clock_out_value)
    lateinit var clockOutValue :TextView

    @BindView(R.id.attendance_button)
    lateinit var attendanceButton: Button

    @BindString(R.string.clock_in)
    lateinit var clockInText: String

    @BindString(R.string.clock_out)
    lateinit var clockOutText: String

    @BindString(R.string.time_empty)
    lateinit var timeEmptyText: String

    @BindView(R.id.no_network_text)
    lateinit var noNetworkText: TextView

    @BindView(R.id.retry)
    lateinit var retryButton: Button

    private lateinit var timeEntryFragment: TimeEntryFragment

    @OnClick(R.id.attendance_button)
    internal fun onClickClockInOrOut() {
        fragmentManager?.beginTransaction()?.replace(
            R.id.container, timeEntryFragment)?.addToBackStack(null)?.commit()
    }

    @OnClick(R.id.retry)
    internal fun onClickRetry() {
        retryButton.visibility = View.GONE
        noNetworkText.visibility = View.GONE

        viewModel.autoLogin()
        viewModel.loadAttendanceTimeInDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        var view = inflater.inflate(R.layout.main_fragment, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        attendanceButton.visibility = View.GONE
        jobInfoContainer.visibility = View.GONE


        viewModel.autoLogin()
        viewModel.loadAttendanceTimeInDetails()

        viewModel.onLoadJobInfo()
            .observe(this, Observer { jobInfo ->
                onLoadJobInfo(jobInfo)
                startListeningToAttendanceState(jobInfo)
            })

        viewModel.onThrowableExceptionHappen()
            .observe(this, Observer {
                
                noNetworkText.text = it.message
                noNetworkText.visibility = View.VISIBLE
                retryButton.visibility = View.VISIBLE
            })

    }

    private fun onLoadJobInfo(jobInfo: JobInformation) {

        attendanceButton.visibility = View.VISIBLE
        jobInfoContainer.visibility = View.VISIBLE

        jobName.text = jobInfo.position.name
        wageValue.text ="Rp " + jobInfo.wage_amount.toDouble().toInt()
        wageType.text = jobInfo.wage_type.replace("_", " ")
        companyName.text = jobInfo.client.name
        companyAddress.text = jobInfo.location.address.street_1
        managerName.text = jobInfo.manager.name

        val underlinedPhoneNumber = SpannableString(jobInfo.manager.phone)
        underlinedPhoneNumber.setSpan(UnderlineSpan(), 0, underlinedPhoneNumber.length, 0)
        managerContactNumberValue.text = underlinedPhoneNumber

        startListeningToTimeEntries()

    }

    private fun startListeningToAttendanceState(jobInfo: JobInformation) {
        viewModel.onLoadAttendanceState()
            .observe(this, Observer { attendanceState ->

                timeEntryFragment =
                    TimeEntryFragment
                        .createWithJobInfoAndAttendanceState(jobInfo, attendanceState)

                when (attendanceState) {
                    AttendanceState.TO_CLOCK_IN -> attendanceButton.text = clockInText
                    AttendanceState.TO_CLOCK_OUT -> attendanceButton.text = clockOutText
                }
            })
    }

    private fun startListeningToTimeEntries() {

        viewModel.onLoadTimeInValue()
            .observe(this, Observer {
                clockInValue.text = it.toLowerCase()
            })

        viewModel.onLoadTimeOutValue()
            .observe(this, Observer {
                clockOutValue.text = it.toLowerCase()

                viewModel.deleteAttendanceState()
                attendanceButton.visibility = View.GONE
            })
    }
}
