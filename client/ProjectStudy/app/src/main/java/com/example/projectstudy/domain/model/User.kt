package com.example.projectstudy.domain.model

data class User(
       val id: String,
    val name: String,
    val username: String,
    val streak: Int = 0,
    val totalHours: Int = 0
)