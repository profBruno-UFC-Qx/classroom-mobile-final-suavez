package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_activities")
data class StudyActivityEntity(
    @PrimaryKey
    val id: String,

    val authorId: String,
    val authorName: String,
    val authorAvatarInitials: String,
    val authorAvatarUrl: String,

    val title: String,
    val subject: String,
    val description: String,

    val durationMinutes: Int,
    val durationSeconds: Int,

    val imageUrl: String,

    val reactions: Int,

    val startedAtMillis: Long,
    val endedAtMillis: Long,
    val createdAtMillis: Long,

    val isManual: Boolean,
    val isSynced: Boolean,
    val pendingSyncAction: String?
)