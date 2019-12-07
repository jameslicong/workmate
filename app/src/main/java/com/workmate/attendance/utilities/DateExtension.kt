package com.workmate.attendance.utilities

import java.text.SimpleDateFormat
import java.util.*

fun String.toTimestampLongValue(): Long {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this).time
}

fun Long.toHoursAndMinuteFormat(): String {
    val simpleDateFormat = SimpleDateFormat("h:mma", Locale.getDefault())
    val computed = this + (TimeZone.getDefault().rawOffset + TimeZone.getDefault().dstSavings)
    return simpleDateFormat.format(Date(computed))
}