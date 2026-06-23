package com.example.projectstudy.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val username: String,
    val email: String = "",
    val institution: String = "",
    val course: String = "",
    val avatarInitials: String = "",
    val avatarUrl: String = "",
    val streakDays: Int = 0,
    val totalMinutes: Int = 0,
    val totalActivities: Int = 0
)