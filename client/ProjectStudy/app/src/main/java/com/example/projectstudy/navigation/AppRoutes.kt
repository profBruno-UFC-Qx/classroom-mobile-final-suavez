package com.example.projectstudy.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


sealed interface AppRoute : NavKey {

    @Serializable
    data object Group : AppRoute

    @Serializable
    data object Ranking : AppRoute

    @Serializable
    data object Profile : AppRoute

    @Serializable
    data class ManualSession(
        val groupId: String
    ) : AppRoute

}