package com.example.projectstudy.core.util

fun Int.toDurationText() : String {
    val hours = this / 60
    val minutes = this % 60

    return when {
        hours > 0 && minutes > 0 -> "${hours}h${minutes}"
        hours > 0 -> "${hours}h"
        else -> "${minutes}min"
    }
}