package com.example.projectstudy.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toHourText(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
    return formatter.format(Date(this))
}