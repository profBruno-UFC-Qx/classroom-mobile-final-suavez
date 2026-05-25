package com.example.projectstudy.domain.model

data class Group(
    val id: String,
    val name: String,
    val memberCount: Int,
    val goalHours: Int,
    val currentHours: Int,
    val ranking: List<User> // lista de user ordenada para a tabela
)