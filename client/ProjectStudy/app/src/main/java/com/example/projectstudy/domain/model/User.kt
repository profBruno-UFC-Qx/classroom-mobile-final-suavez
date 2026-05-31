package com.example.projectstudy.domain.model

data class User(
    val id: String,
    val name: String,
    val username: String,

    val email: String = "",
    val institution: String = "", //ainda a decidir
    val course: String = "", //ainda a decidir

    val avatarInitials: String,
    val avatarUrl: String = "",

    val streakDays: Int = 0,
    val totalMinutes: Int = 0,
    val totalActivities: Int = 0
)