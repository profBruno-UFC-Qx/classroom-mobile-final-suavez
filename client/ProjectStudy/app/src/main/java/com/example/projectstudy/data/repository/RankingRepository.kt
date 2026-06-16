package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.RankingEntry
import kotlinx.coroutines.flow.Flow

interface RankingRepository {
    fun observeRankingByGroupId(
        groupId: String
    ): Flow<List<RankingEntry>>
}