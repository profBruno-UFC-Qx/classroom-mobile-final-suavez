package com.example.projectstudy.domain.model

data class ActivityAuthor(
    val id: String,
    val name: String,
    val avatarInitials: String,
    val avatarUrl: String = ""
)