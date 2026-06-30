package com.example.projectstudy.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
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
import androidx.compose.ui.graphics.Color
import com.example.projectstudy.features.profile.screens.ProfileSettingsScreen

/**
 * Componente responsável pela navegação principal do app.
 *
 * Controla o fluxo entre autenticação, feed, ranking, perfil e criação manual
 * de sessão de estudo. A rota inicial é definida com base no estado de
 * autenticação persistido localmente.
 *
 * A barra inferior é exibida como um componente flutuante, pequeno e centralizado,
 * sem uso de Scaffold.
 */
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
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
                            ProfileScreen(
                                onSettingsClick = {
                                    backStack.add(AppRoute.ProfileSettings)
                                }
                            )
                        }

                        AppRoute.ProfileSettings -> NavEntry(route) {
                            ProfileSettingsScreen(
                                onNavigateBack = {
                                    goBack()
                                },
                                onLogoutClick = {
                                    backStack.clear()
                                    backStack.add(AppRoute.Login)
                                }
                            )
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

        if (showBottomBar) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(96.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.88f),
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(bottom = 4.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
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
        }
    }
}