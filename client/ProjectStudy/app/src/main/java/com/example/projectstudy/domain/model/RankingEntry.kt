package com.example.projectstudy.domain.model

data class RankingEntry(
    val groupId: String,
    val user: User,

    val position: Int,

    val totalMinutes: Int,
    val activeDays: Int,

    val isCurrentUser: Boolean = false
)