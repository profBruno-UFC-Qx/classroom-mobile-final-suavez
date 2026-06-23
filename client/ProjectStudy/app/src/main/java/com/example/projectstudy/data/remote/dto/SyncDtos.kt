package com.example.projectstudy.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActivityAuthorRemoteDto(
    val id: String,
    val name: String,
    val avatarInitials: String = "",
    val avatarUrl: String = ""
)

@Serializable
data class StudyActivityRemoteDto(
    val id: String,
    val groupIds: List<String> = emptyList(),
    val author: ActivityAuthorRemoteDto,
    val title: String,
    val subject: String,
    val description: String = "",
    val durationMinutes: Int,
    val durationSeconds: Int = 0,
    val imageUrl: String = "",
    val mediaUris: List<String> = emptyList(),
    val reactions: Int = 0,
    val startedAtMillis: Long,
    val endedAtMillis: Long,
    val createdAtMillis: Long,
    val updatedAtMillis: Long = 0L,
    val isManual: Boolean = false
)

@Serializable
data class GroupRemoteDto(
    val id: String,
    val name: String,
    val description: String = "",
    val bannerUrl: String = "",
    val inviteCode: String = "",
    val memberCount: Int = 0,
    val goalMinutes: Int = 0,
    val currentMinutes: Int = 0,
    val userRankingPosition: Int = 0,
    val userMinutes: Int = 0,
    val rankingMetric: String = "TIME",
    val createdAtMillis: Long,
    val updatedAtMillis: Long = 0L,
    val isActive: Boolean = true
)

@Serializable
data class SyncPullResponseDto(
    val activities: List<StudyActivityRemoteDto> = emptyList(),
    val groups: List<GroupRemoteDto> = emptyList(),
    val serverTimestamp: Long
)
