package com.workmate.attendance.utilities

class Constants {

    object SharedPrefKeys {
        val API_KEY = "apiKey"
        val CLOCK_IN = "clock_in"
        val CLOCK_OUT = "clock_out"
    }

    enum class AttendanceState(val value: Int) {
        TO_CLOCK_IN(0),
        TO_CLOCK_OUT(1)
    }
}