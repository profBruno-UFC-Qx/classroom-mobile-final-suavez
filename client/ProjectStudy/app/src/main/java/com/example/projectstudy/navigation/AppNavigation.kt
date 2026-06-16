package com.example.projectstudy.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.projectstudy.features.auth.screens.LoginScreen
import com.example.projectstudy.features.auth.screens.RegisterScreen
import com.example.projectstudy.features.auth.viewmodel.AuthSessionViewModel
import com.example.projectstudy.features.feed.screens.FeedScreen
import com.example.projectstudy.features.profile.screens.ProfileScreen
import com.example.projectstudy.features.ranking.screens.RankingScreen
import com.example.projectstudy.features.session.screens.ManualSessionScreen
import com.example.projectstudy.ui.components.LumioBottomBar

@Composable
fun AppNavigation() {
    val authSessionViewModel: AuthSessionViewModel = hiltViewModel()
    val isLoggedIn by authSessionViewModel.isLoggedIn.collectAsState()

    val backStack = remember {
        mutableStateListOf<AppRoute>(
            if (isLoggedIn) {
                AppRoute.Group
            } else {
                AppRoute.Login
            }
        )
    }

    LaunchedEffect(isLoggedIn) {
        val currentRoute = backStack.lastOrNull()

        if (
            isLoggedIn &&
            (currentRoute == AppRoute.Login || currentRoute == AppRoute.Register)
        ) {
            backStack.clear()
            backStack.add(AppRoute.Group)
        }

        if (
            !isLoggedIn &&
            currentRoute != AppRoute.Login &&
            currentRoute != AppRoute.Register
        ) {
            backStack.clear()
            backStack.add(AppRoute.Login)
        }
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
                        if (currentRoute == tab.route) {
                            return@LumioBottomBar
                        }

                        backStack.removeAll { route ->
                            route != AppRoute.Group
                        }

                        if (tab.route != AppRoute.Group) {
                            backStack.add(tab.route)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            NavDisplay(
                backStack = backStack,
                onBack = {
                    goBack()
                },
                entryProvider = { route ->
                    when (route) {
                        AppRoute.Login -> NavEntry(route) {
                            LoginScreen(
                                onLoginSuccess = {
                                    backStack.clear()
                                    backStack.add(AppRoute.Group)
                                },
                                onRegisterClick = {
                                    backStack.add(AppRoute.Register)
                                }
                            )
                        }

                        AppRoute.Register -> NavEntry(route) {
                            RegisterScreen(
                                onRegisterSuccess = {
                                    backStack.clear()
                                    backStack.add(AppRoute.Group)
                                },
                                onLoginClick = {
                                    backStack.clear()
                                    backStack.add(AppRoute.Login)
                                }
                            )
                        }

                        AppRoute.Group -> NavEntry(route) {
                            FeedScreen(
                                onAddSessionClick = { groupId ->
                                    backStack.add(
                                        AppRoute.ManualSession(groupId)
                                    )
                                }
                            )
                        }

                        AppRoute.Ranking -> NavEntry(route) {
                            RankingScreen()
                        }

                        AppRoute.Profile -> NavEntry(route) {
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