package com.example.projectstudy.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.projectstudy.features.feed.screens.FeedScreen
import com.example.projectstudy.features.profile.screens.ProfileScreen
import com.example.projectstudy.features.session.screens.ManualSessionScreen
import com.example.projectstudy.ui.components.LumioBottomBar

@Composable
fun AppNavigation() {
    val backStack = remember {
        mutableStateListOf<AppRoute>(
            AppRoute.Group
        )
    }

    val currentRoute = backStack.lastOrNull()

    val showBottomBar = currentRoute == AppRoute.Group ||
            currentRoute == AppRoute.Ranking ||
            currentRoute == AppRoute.Profile

    fun goBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                LumioBottomBar(
                    currentRoute = currentRoute,
                    onTabSelected = { tab ->
                        backStack.clear()
                        backStack.add(tab.route)
                    }
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier.padding(padding)
        ) {
            NavDisplay(
                backStack = backStack,
                onBack = {
                    goBack()
                },
                entryProvider = { route ->
                    when (route) {
                        AppRoute.Group -> NavEntry(AppRoute.Group) {
                            FeedScreen(
                                onAddSessionClick = { groupId ->
                                    backStack.add(
                                        AppRoute.ManualSession(groupId)
                                    )
                                }
                            )
                        }

                        AppRoute.Ranking -> NavEntry(AppRoute.Ranking) {
                            RankingPlaceholderScreen()
                        }

                        AppRoute.Profile -> NavEntry(AppRoute.Profile) {
                            ProfileScreen()
                        }

                        is AppRoute.ManualSession -> NavEntry(route) {
                            ManualSessionScreen(
                                onBackClick = {
                                    goBack()
                                },
                                onPublished = {
                                    goBack()
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}