package com.example.projectstudy.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(

    titleLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp
    ),

    titleMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Black,
        fontSize = 18.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    labelMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    )
)