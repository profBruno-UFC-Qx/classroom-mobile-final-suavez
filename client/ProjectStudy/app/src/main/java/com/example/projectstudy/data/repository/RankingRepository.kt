package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.RankingEntry

interface RankingRepository {

    suspend fun getRankingByGroupId(
        groupId: String
    ): List<RankingEntry>
}