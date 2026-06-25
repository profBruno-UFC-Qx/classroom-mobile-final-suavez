package com.example.projectstudy.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MediaUploadResponseDto(
    val imageUrl: String
)