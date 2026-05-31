package com.example.projectstudy.domain.model

data class Group(
    val id: String,
    val name: String,
    val description: String,
    val bannerUrl: String,

    val inviteCode: String,

    val memberCount: Int,

    val goalMinutes: Int,
    val currentMinutes: Int,

    val userRankingPosition: Int,
    val userMinutes: Int,

    val rankingMetric: RankingMetric,

    val createdAtMillis: Long, //epoch milliseconds

    val isActive: Boolean = true
)