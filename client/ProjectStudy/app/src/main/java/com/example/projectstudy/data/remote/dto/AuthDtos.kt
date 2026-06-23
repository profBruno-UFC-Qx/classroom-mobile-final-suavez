package com.example.projectstudy.data.remote.dto

import kotlinx.serialization.SerialName
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
data class RegisterResponseDto(
    val accessToken: String,
    val tokenType: String,
    val user: UserDto
)

@Serializable
data class LoginResponseDto(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("token_type")
    val tokenType: String,

    val user: UserDto
)
