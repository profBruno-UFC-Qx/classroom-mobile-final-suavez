package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ranking_entries")
data class RankingEntryEntity(
    @PrimaryKey
    val id: String,

    val groupId: String,

    val userId: String,
    val userDisplayName: String,
    val username: String,

    val userEmail: String,
    val userInstitution: String,
    val userCourse: String,

    val userAvatarInitials: String,
    val userAvatarUrl: String,

    val totalMinutes: Int,
    val activeDays: Int,
    val position: Int,
    val isCurrentUser: Boolean,

    val lastSyncedAtMillis: Long
)