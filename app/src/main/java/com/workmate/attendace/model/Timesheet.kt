package com.workmate.attendace.model

data class Timesheet (
    val id: String,
    val clock_in_time: String,
    val clock_out_time: String,
    val clock_in_latitude: String,
    val clock_in_longitude: String,
    val clock_out_latitude: String,
    val clock_out_longitude: String,
    val schedule: String?
)