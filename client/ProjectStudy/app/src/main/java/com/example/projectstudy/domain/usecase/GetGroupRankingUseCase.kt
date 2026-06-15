package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.RankingRepository
import com.example.projectstudy.domain.model.RankingEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupRankingUseCase @Inject constructor(
    private val rankingRepository: RankingRepository
) {

    operator fun invoke(
        groupId: String
    ): Flow<List<RankingEntry>> {
        return rankingRepository.observeRankingByGroupId(groupId)
    }
}