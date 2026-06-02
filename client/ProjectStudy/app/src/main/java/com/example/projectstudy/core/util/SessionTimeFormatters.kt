package com.example.projectstudy.core.util

import java.util.Calendar

fun buildStartedAtMillis(
    dateMillis: Long,
    startTimeMinutes: Int
): Long {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = dateMillis

        set(Calendar.HOUR_OF_DAY, startTimeMinutes / 60)
        set(Calendar.MINUTE, startTimeMinutes % 60)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return calendar.timeInMillis
}

fun buildEndedAtMillis(
    startedAtMillis: Long,
    durationMinutes: Int
): Long {
    return startedAtMillis + durationMinutes * 60_000L
}

fun Int.toTimeText(): String {
    val hour = this / 60
    val minute = this % 60

    return "%02d:%02d".format(hour, minute)
}

fun getEstimatedEndTimeText(
    startTimeMinutes: Int,
    durationMinutes: Int
): String {
    val totalMinutes = startTimeMinutes + durationMinutes
    val endTimeMinutes = totalMinutes % (24 * 60)

    val daySuffix = if (totalMinutes >= 24 * 60) {
        " +1 dia"
    } else {
        ""
    }

    return "${endTimeMinutes.toTimeText()}$daySuffix"
}
