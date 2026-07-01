package com.example.projectstudy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainBottomTab(
    val route: AppRoute,
    val label: String,
    val icon: ImageVector
) {
    GROUP(
        route = AppRoute.Group,
        label = "Grupo",
        icon = Icons.Outlined.Groups
    ),

    RANKING(
        route =  AppRoute.Ranking,
        label = "Ranking",
        icon = Icons.Outlined.Leaderboard
    ),

    PROFILE(
        route = AppRoute.Profile,
        label = "Perfil",
        icon = Icons.Outlined.Person
    )
}