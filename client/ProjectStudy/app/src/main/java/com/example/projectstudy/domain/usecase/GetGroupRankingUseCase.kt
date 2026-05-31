package com.example.projectstudy.domain.usecase

import com.example.projectstudy.domain.repository.RankingRepository
import com.example.projectstudy.domain.model.RankingEntry
import javax.inject.Inject


class GetGroupRankingUseCase @Inject constructor(
    private val repository: RankingRepository
){

    suspend operator fun invoke(groupId: String): List<RankingEntry> {
        return repository.getRankingByGroupId(groupId)
    }
}