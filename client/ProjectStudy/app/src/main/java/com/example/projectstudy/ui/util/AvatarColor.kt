package com.example.projectstudy.ui.util

import androidx.compose.ui.graphics.Color

fun String.toAvatarColor(): Color {
    val color = listOf(
        Color(0xFF639922), // verde Lumio
        Color(0xFF5DCAA5), // verde água
        Color(0xFFAFA9EC), // roxo suave
        Color(0xFFF2A65A), // laranja suave
        Color(0xFFE57373), // vermelho suave
        Color(0xFF64B5F6), // azul suave
        Color(0xFFFFD166), // amarelo suave
    )

    val index = this
        .ifBlank { "?" }
        .sumOf { char -> char.code}
        .mod(color.size)

    return color[index]
}