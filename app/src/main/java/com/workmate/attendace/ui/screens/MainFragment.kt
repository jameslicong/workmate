package com.workmate.attendace.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.workmate.attendace.R
import com.workmate.attendace.model.JobInformation
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

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

        viewModel.autoLogin()

        viewModel.onLoadJobInfo()
            .observe(this, Observer {

            })
    }

    private fun onLoadJobInfo(jobInfo: JobInformation) {

        jobName.text = jobInfo.position.name
        wageValue.text ="Rp" + jobInfo.wage_amount
//        company.
    }

}
