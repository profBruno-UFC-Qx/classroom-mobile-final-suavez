package com.example.projectstudy.features.profile.state

import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.model.User

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val user: User? = null,
    val recentActivities: List<StudyActivity> = emptyList(),
    val totalMinutes: Int = 0,
    val totalSessions: Int = 0,
    val activeDays: Int = 0,
    val streakDays: Int = 0,
    val error: String? = null
)