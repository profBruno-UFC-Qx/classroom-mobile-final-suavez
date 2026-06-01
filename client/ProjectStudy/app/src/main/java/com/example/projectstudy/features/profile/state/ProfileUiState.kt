package com.example.projectstudy.features.profile.state

import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.model.User

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val recentActivities: List<StudyActivity> = emptyList(),
    val error: String? = null
)