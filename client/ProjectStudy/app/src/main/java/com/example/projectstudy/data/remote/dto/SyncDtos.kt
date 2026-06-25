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
data class RankingUserRemoteDto(
    val id: String,
    val name: String,
    val username: String,
    val email: String = "",
    val institution: String = "",
    val course: String = "",
    val avatarInitials: String = "",
    val avatarUrl: String = ""
)

@Serializable
data class RankingEntryRemoteDto(
    val groupId: String,
    val user: RankingUserRemoteDto,
    val totalMinutes: Int,
    val activeDays: Int,
    val position: Int,
    val isCurrentUser: Boolean,
    val updatedAtMillis: Long
)

@Serializable
data class SyncPullResponseDto(
    val activities: List<StudyActivityRemoteDto> = emptyList(),
    val groups: List<GroupRemoteDto> = emptyList(),
    val rankingEntries: List<RankingEntryRemoteDto> = emptyList(),
    val serverTimestamp: Long
)

@Serializable
data class SyncActivityRequestDto(
    val id: String,
    val authorId: String,
    val title: String,
    val subject: String,
    val description: String,
    val durationMinutes: Int,
    val durationSeconds: Int,
    val imageUrl: String = "",
    val groupIds: List<String> = emptyList(),
    val mediaUris: List<String> = emptyList(),
    val reactions: Int = 0,
    val startedAtMillis: Long,
    val endedAtMillis: Long,
    val createdAtMillis: Long,
    val updatedAtMillis: Long? = null,
    val isManual: Boolean,
    val pendingSyncAction: String
)

@Serializable
data class SyncActivityResponseDto(
    val syncedIds: List<String> = emptyList(),
    val failedIds: List<String> = emptyList()
)