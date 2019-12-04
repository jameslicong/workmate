package com.workmate.attendace.model

data class Attendance(
    val timesheet: Timesheet,
    val require_feedback: Boolean
)
