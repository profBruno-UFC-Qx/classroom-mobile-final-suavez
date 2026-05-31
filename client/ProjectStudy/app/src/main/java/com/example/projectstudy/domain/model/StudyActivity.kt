package com.example.projectstudy.domain.model

data class StudyActivity(
    val id: String,

    val groupId: String,

    val title: String,
    val subject: String,
    val description: String,

    val durationMinutes: Int,

    val author: ActivityAuthor,

    val imageUrl: String,
    val reactions: Int,

    val createdAtMillis: Long,

    val isManual: Boolean = false
)