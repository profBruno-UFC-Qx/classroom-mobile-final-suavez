package com.example.projectstudy.domain.model

data class Activity(
    val id: String,
    val user: User,
    val subject: String,
    val duration: String,
    val description: String? = null,
    val photoResId: Int, // referencia para a imagem local (R.drawable.foto) - pois os dados sao mock
    val reactionsCount: Int = 0
)