package com.example.projectstudy.features.ranking.state

import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.RankingEntry

data class RankingUiState(
    val isLoading: Boolean = true,
    val group: Group? = null,
    val entries: List<RankingEntry> = emptyList(),
    val error: String? = null
)