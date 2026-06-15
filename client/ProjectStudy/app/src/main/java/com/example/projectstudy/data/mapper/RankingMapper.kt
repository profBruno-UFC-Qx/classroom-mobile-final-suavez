package com.example.projectstudy.data.mapper

import com.example.projectstudy.data.local.entity.RankingEntryEntity
import com.example.projectstudy.domain.model.RankingEntry
import com.example.projectstudy.domain.model.User

fun RankingEntryEntity.toDomain(): RankingEntry {
    return RankingEntry(
        groupId = groupId,
        user = User(
            id = userId,
            name = userDisplayName,
            username = username,
            email = userEmail,
            institution = userInstitution,
            course = userCourse,
            avatarInitials = userAvatarInitials,
            avatarUrl = userAvatarUrl
        ),
        totalMinutes = totalMinutes,
        activeDays = activeDays,
        position = position,
        isCurrentUser = isCurrentUser
    )
}

fun RankingEntry.toEntity(
    lastSyncedAtMillis: Long = System.currentTimeMillis()
): RankingEntryEntity {
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

        lastSyncedAtMillis = lastSyncedAtMillis
    )
}