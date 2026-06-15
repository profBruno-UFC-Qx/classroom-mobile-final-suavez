package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.RankingRepository
import com.example.projectstudy.domain.model.RankingEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRankingRepository @Inject constructor(
    private val rankingDao: RankingDao,
    private val localDataSeeder: LocalDataSeeder
) : RankingRepository {

    override fun observeRankingByGroupId(
        groupId: String
    ): Flow<List<RankingEntry>> {
        return flow {
            localDataSeeder.seedIfNeeded()

            rankingDao.observeRankingByGroupId(groupId)
                .map { entries ->
                    entries.map { entry ->
                        entry.toDomain()
                    }
                }
                .collect { ranking ->
                    emit(ranking)
                }
        }
    }
}