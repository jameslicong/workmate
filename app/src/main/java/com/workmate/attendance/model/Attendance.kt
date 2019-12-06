package com.workmate.attendance.model

data class Attendance(
    val timesheet: Timesheet,
    val require_feedback: Boolean
)
