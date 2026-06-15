package com.example.projectstudy.data.mapper

import com.example.projectstudy.data.local.entity.UserEntity
import com.example.projectstudy.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        username = username,
        email = email,
        institution = institution,
        course = course,
        avatarInitials = avatarInitials,
        avatarUrl = avatarUrl,
        streakDays = streakDays,
        totalMinutes = totalMinutes,
        totalActivities = totalActivities
    )
}

fun User.toEntity(
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