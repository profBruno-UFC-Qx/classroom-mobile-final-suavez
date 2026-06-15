package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val username: String,

    val email: String,
    val institution: String,
    val course: String,

    val avatarInitials: String,
    val avatarUrl: String,

    val totalMinutes: Int,
    val totalActivities: Int,
    val streakDays: Int,

    val lastSyncedAtMillis: Long
)