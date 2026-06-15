package com.example.projectstudy.data.mapper

import com.example.projectstudy.data.local.entity.GroupEntity
import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.RankingMetric

fun GroupEntity.toDomain(): Group {
    return Group(
        id = id,
        name = name,
        description = description,
        bannerUrl = bannerUrl,
        memberCount = memberCount,
        inviteCode = inviteCode,
        goalMinutes = goalMinutes,
        currentMinutes = currentMinutes,
        userRankingPosition = userRankingPosition,
        userMinutes = userMinutes,
        rankingMetric = rankingMetric.toRankingMetric(),
        createdAtMillis = createdAtMillis
    )
}

fun Group.toEntity(
    lastSyncedAtMillis: Long = System.currentTimeMillis()
): GroupEntity {
    return GroupEntity(
        id = id,
        name = name,
        description = description,
        bannerUrl = bannerUrl,
        memberCount = memberCount,
        inviteCode = inviteCode,
        goalMinutes = goalMinutes,
        currentMinutes = currentMinutes,
        userRankingPosition = userRankingPosition,
        userMinutes = userMinutes,
        rankingMetric = rankingMetric.name,
        createdAtMillis = createdAtMillis,
        lastSyncedAtMillis = lastSyncedAtMillis
    )
}

private fun String.toRankingMetric(): RankingMetric {
    return enumValues<RankingMetric>()
        .firstOrNull { metric ->
            metric.name == this
        }
        ?: enumValues<RankingMetric>().first()
}