package com.example.projectstudy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.projectstudy.data.local.entity.RankingEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RankingDao {
    @Query(
        """
        SELECT * FROM ranking_entries
        WHERE groupId = :groupId
        ORDER BY position ASC
        """
    )
    fun observeRankingByGroupId(
        groupId: String
    ): Flow<List<RankingEntryEntity>>

    @Query(
        """
        SELECT * FROM ranking_entries
        WHERE groupId = :groupId
        ORDER BY position ASC
        """
    )
    suspend fun getRankingByGroupIdOnce(
        groupId: String
    ): List<RankingEntryEntity>

    @Upsert
    suspend fun upsertRanking(
        entries: List<RankingEntryEntity>
    )

    @Query("DELETE FROM ranking_entries WHERE groupId = :groupId")
    suspend fun deleteRankingByGroupId(
        groupId: String
    )
}