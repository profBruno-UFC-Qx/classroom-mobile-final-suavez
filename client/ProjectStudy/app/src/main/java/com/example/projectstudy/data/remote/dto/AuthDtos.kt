package com.example.projectstudy.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val institution: String,
    val course: String
)

@Serializable
data class AuthResponseDto(
    val accessToken: String,
    val tokenType: String,
    val user: UserDto
)
