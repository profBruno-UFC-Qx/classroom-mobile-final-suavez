package com.example.projectstudy.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.projectstudy.features.feed.screens.FeedScreen
import com.example.projectstudy.features.profile.screens.ProfileScreen
import com.example.projectstudy.features.session.screens.ManualSessionScreen
import com.example.projectstudy.ui.components.LumioBottomBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

@Composable
fun AppNavigation() {
    val backStack = remember {
        mutableStateListOf<AppRoute>(AppRoute.Group)
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
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                LumioBottomBar(
                    currentRoute = currentRoute,
                    onTabSelected = { tab ->
                        if (currentRoute == tab.route) return@LumioBottomBar

                        backStack.removeAll { it != AppRoute.Group }

                        if (tab.route != AppRoute.Group) {
                            backStack.add(tab.route)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavDisplay(
                backStack = backStack,
                onBack = { goBack() },
                entryProvider = { route ->
                    when (route) {
                        AppRoute.Group -> NavEntry(route) {
                            FeedScreen(
                                onAddSessionClick = { groupId ->
                                    backStack.add(AppRoute.ManualSession(groupId))
                                }
                            )
                        }

                        AppRoute.Ranking -> NavEntry(route) {
                            RankingPlaceholderScreen()
                        }

                        AppRoute.Profile -> NavEntry(route) {
                            ProfileScreen()
                        }

                        is AppRoute.ManualSession -> NavEntry(route) {
                            ManualSessionScreen(
                                onBackClick = { goBack() },
                                onPublished = { goBack() }
                            )
                        }
                    }
                }
            )
        }
    }
}