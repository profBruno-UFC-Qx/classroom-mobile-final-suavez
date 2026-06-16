package com.example.projectstudy.data.mock

import com.example.projectstudy.data.repository.RankingRepository
import com.example.projectstudy.domain.model.RankingEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeRankingRepository @Inject constructor() : RankingRepository {
    override fun observeRankingByGroupId(
        groupId: String
    ): Flow<List<RankingEntry>> {
        return flow {
            delay(500)

            emit(
                MockData.getRankingByGroupId(groupId)
            )
        }
    }
}