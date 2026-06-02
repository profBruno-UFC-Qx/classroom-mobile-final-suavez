package com.example.projectstudy.domain.model

data class StudyActivity(
    val id: String,
    val groupIds: List<String>,
    val author: ActivityAuthor,

    val title: String,
    val subject: String,
    val description: String,

    val durationMinutes: Int,

    val imageUrl: String,
    val mediaUris: List<String> = emptyList(),

    val reactions: Int,

    val startedAtMillis: Long,
    val endedAtMillis: Long,
    val createdAtMillis: Long,

    val isManual: Boolean = false
)