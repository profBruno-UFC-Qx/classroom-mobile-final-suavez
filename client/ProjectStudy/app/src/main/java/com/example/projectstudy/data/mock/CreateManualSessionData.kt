package com.example.projectstudy.domain.model

data class CreateManualSessionData(
    val title: String,
    val subject: String,
    val description: String,
    val durationMinutes: Int,
    val imageUrl: String,
    val dateMillis: Long,
    val startTimeMinutes: Int,
    val endTimeMinutes: Int,
    val groupIds: List<String>
)