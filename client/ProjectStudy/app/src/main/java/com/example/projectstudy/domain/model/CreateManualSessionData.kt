package com.example.projectstudy.domain.model

data class CreateManualSessionData(
    val title: String,
    val subject: String,
    val description: String,

    val dateMillis: Long,
    val startTimeMinutes: Int,
    val durationMinutes: Int,

    val mediaUris: List<String>,

    val groupIds: List<String>
)