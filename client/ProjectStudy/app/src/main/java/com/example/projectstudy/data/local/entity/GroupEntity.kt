package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey
    val id: String,

    val name: String,
    val description: String,
    val bannerUrl: String,

    val memberCount: Int,
    val inviteCode: String,

    val goalMinutes: Int,
    val currentMinutes: Int,

    val userRankingPosition: Int,
    val userMinutes: Int,

    val rankingMetric: String,

    val createdAtMillis: Long,
    val lastSyncedAtMillis: Long
)