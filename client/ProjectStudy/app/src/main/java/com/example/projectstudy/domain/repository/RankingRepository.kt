package com.example.projectstudy.domain.repository

import com.example.projectstudy.domain.model.RankingEntry

interface RankingRepository {

    suspend fun getRankingByGroupId(
        groupId: String
    ): List<RankingEntry>
}