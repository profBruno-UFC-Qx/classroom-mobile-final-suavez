package com.example.projectstudy.features.feed.state

import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.RankingEntry

data class FeedUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val group: Group? = null,
    val ranking: List<RankingEntry> = emptyList(),
    val activities: List<StudyActivity> = emptyList(),
    val error: String? = null
)