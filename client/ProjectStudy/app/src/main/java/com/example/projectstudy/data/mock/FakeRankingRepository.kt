package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.RankingEntry
import com.example.projectstudy.domain.repository.RankingRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeRankingRepository @Inject constructor(): RankingRepository {

    override suspend fun getRankingByGroupId(groupId: String): List<RankingEntry> {
        delay(500)

        return MockData.getRankingByGroupId(groupId)
    }
}