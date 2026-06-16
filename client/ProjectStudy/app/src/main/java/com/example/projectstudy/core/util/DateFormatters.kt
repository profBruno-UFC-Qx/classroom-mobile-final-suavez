package com.example.projectstudy.core.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.toFeedDateLabel(): String {
    val activityCalendar = Calendar.getInstance().apply {
        timeInMillis = this@toFeedDateLabel
    }

    val todayCalendar = Calendar.getInstance()

    val yesterdayCalendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }

    return when {
        activityCalendar.isSameDay(todayCalendar) -> {
            "Hoje"
        }

        activityCalendar.isSameDay(yesterdayCalendar) -> {
            "Ontem"
        }

        else -> {
            val formatter = SimpleDateFormat(
                "EEEE, MMM. dd",
                Locale("pt", "BR")
            )

            formatter.format(Date(this))
        }
    }
}

fun Long.toHourText(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
    return formatter.format(Date(this))
}

fun Int.toHourMinuteText(): String {
    val totalMinutes = this.coerceAtLeast(0)

    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return if (hours > 0) {
        "${hours}h${minutes.toString().padStart(2, '0')}"
    } else {
        "${minutes}min"
    }
}

private fun Calendar.isSameDay(other: Calendar): Boolean {
    return get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)
}

