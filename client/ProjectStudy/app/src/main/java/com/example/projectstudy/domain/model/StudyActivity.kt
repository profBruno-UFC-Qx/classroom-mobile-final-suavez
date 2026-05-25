package com.example.projectstudy.domain.model

data class StudyActivity(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val userName: String,
    val userAvatar: String,
    val imageUrl: String,
    val reactions: Int,
    val createdAt: String
)