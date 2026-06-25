package com.example.projectstudy.data.remote.mapper

import com.example.projectstudy.data.local.entity.UserEntity
import com.example.projectstudy.data.remote.dto.UserDto

fun UserDto.toEntity(
    lastSyncedAtMillis: Long = System.currentTimeMillis()
): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        username = username,
        email = email,
        institution = institution,
        course = course,
        avatarInitials = avatarInitials,
        avatarUrl = avatarUrl,
        totalMinutes = totalMinutes,
        totalActivities = totalActivities,
        streakDays = streakDays,
        lastSyncedAtMillis = lastSyncedAtMillis
    )
}
