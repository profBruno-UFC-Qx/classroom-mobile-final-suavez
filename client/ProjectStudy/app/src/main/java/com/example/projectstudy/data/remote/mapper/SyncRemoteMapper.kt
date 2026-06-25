package com.example.projectstudy.data.remote.mapper

import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity
import com.example.projectstudy.data.local.entity.GroupEntity
import com.example.projectstudy.data.local.entity.RankingEntryEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity
import com.example.projectstudy.data.remote.dto.GroupRemoteDto
import com.example.projectstudy.data.remote.dto.RankingEntryRemoteDto
import com.example.projectstudy.data.remote.dto.StudyActivityRemoteDto
import com.example.projectstudy.data.remote.dto.SyncActivityRequestDto

fun GroupRemoteDto.toEntity(
    serverTimestamp: Long
): GroupEntity {
    val syncedAt = if (updatedAtMillis > 0L) {
        updatedAtMillis
    } else {
        serverTimestamp
    }

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
        rankingMetric = rankingMetric,
        createdAtMillis = createdAtMillis,
        lastSyncedAtMillis = syncedAt
    )
}

fun StudyActivityRemoteDto.toEntity(): StudyActivityEntity {
    val safeDurationSeconds = if (durationSeconds > 0) {
        durationSeconds
    } else {
        durationMinutes * 60
    }

    return StudyActivityEntity(
        id = id,
        authorId = author.id,
        authorName = author.name,
        authorAvatarInitials = author.avatarInitials,
        authorAvatarUrl = author.avatarUrl,
        title = title,
        subject = subject,
        description = description,
        durationMinutes = durationMinutes,
        durationSeconds = safeDurationSeconds,
        imageUrl = imageUrl,
        reactions = reactions,
        startedAtMillis = startedAtMillis,
        endedAtMillis = endedAtMillis,
        createdAtMillis = createdAtMillis,
        isManual = isManual,
        isSynced = true,
        pendingSyncAction = null
    )
}

fun StudyActivityRemoteDto.toGroupRefs(): List<ActivityGroupCrossRef> {
    return groupIds.map { groupId ->
        ActivityGroupCrossRef(
            activityId = id,
            groupId = groupId
        )
    }
}

fun StudyActivityRemoteDto.toMediaEntities(): List<ActivityMediaEntity> {
    return mediaUris.mapIndexed { index, uri ->
        ActivityMediaEntity(
            id = "${id}_media_$index",
            activityId = id,
            uri = uri,
            position = index
        )
    }
}

fun StudyActivityEntity.toSyncRequest(
    groupIds: List<String>,
    mediaUris: List<String>
): SyncActivityRequestDto {
    return SyncActivityRequestDto(
        id = id,
        authorId = authorId,
        title = title,
        subject = subject,
        description = description,
        durationMinutes = durationMinutes,
        durationSeconds = durationSeconds,
        imageUrl = imageUrl,
        groupIds = groupIds,
        mediaUris = mediaUris,
        reactions = reactions,
        startedAtMillis = startedAtMillis,
        endedAtMillis = endedAtMillis,
        createdAtMillis = createdAtMillis,
        updatedAtMillis = null,
        isManual = isManual,
        pendingSyncAction = pendingSyncAction ?: "CREATE"
    )
}

fun RankingEntryRemoteDto.toEntity(): RankingEntryEntity {
    return RankingEntryEntity(
        id = "${groupId}_${user.id}",
        groupId = groupId,
        userId = user.id,
        userDisplayName = user.name,
        username = user.username,
        userEmail = user.email,
        userInstitution = user.institution,
        userCourse = user.course,
        userAvatarInitials = user.avatarInitials,
        userAvatarUrl = user.avatarUrl,
        totalMinutes = totalMinutes,
        activeDays = activeDays,
        position = position,
        isCurrentUser = isCurrentUser,
        lastSyncedAtMillis = updatedAtMillis
    )
}