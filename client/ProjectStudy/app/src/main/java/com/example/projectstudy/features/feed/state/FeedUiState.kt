package com.example.projectstudy.features.feed.state

import com.example.projectstudy.domain.model.StudyActivity

data class FeedUiState(
    val isLoading: Boolean = false,
    val activities: List<StudyActivity> = emptyList(),
    val error: String? = null
)