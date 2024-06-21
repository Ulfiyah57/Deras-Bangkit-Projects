package com.deras.id.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun convertMillisToDateString(millis: Comparable<*>): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd | HH:mm", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis as Long
    return sdf.format(calendar.time)
}