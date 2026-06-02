package com.example.projectstudy.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.projectstudy.features.feed.screens.FeedScreen
import com.example.projectstudy.features.profile.screens.ProfileScreen
import com.example.projectstudy.ui.components.LumioBottomBar
import com.example.projectstudy.features.session.screens.ManualSessionScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            LumioBottomBar(
                currentRoute = currentRoute,
                onTabSelected = { tab ->
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = MainBottomTab.GROUP.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(MainBottomTab.GROUP.route) {
                FeedScreen(
                    onAddSessionClick = { groupId ->
                        navController.navigate(
                            AppRoutes.manualSession(groupId)
                        )
                    }
                )
            }

            composable(MainBottomTab.RANKING.route) {
                RankingPlaceholderScreen()
            }

            composable(MainBottomTab.PROFILE.route) {
                ProfileScreen()
            }

            composable(
                route = AppRoutes.MANUAL_SESSION_WITH_GROUP
            ) {
                ManualSessionScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onPublished = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}