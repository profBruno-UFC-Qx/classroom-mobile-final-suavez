package com.example.projectstudy.features.profile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectstudy.features.profile.components.ProfileBadgeRow
import com.example.projectstudy.features.profile.components.ProfileHeader
import com.example.projectstudy.features.profile.components.ProfileStatsRow
import com.example.projectstudy.features.profile.components.ProfileStreakCard
import com.example.projectstudy.features.profile.components.RecentSessionItem
import com.example.projectstudy.features.profile.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.error != null -> {
                Text(
                    text = uiState.error ?: "Erro ao carregar perfil",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }

            uiState.user != null -> {
                val user = uiState.user!!

                PullToRefreshBox(
                    modifier = Modifier.fillMaxSize(),
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = {
                        viewModel.refreshRemoteData()
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 12.dp,
                            end = 16.dp,
                            bottom = 96.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            ProfileHeader(
                                user = user
                            )
                        }

                        item {
                            ProfileStatsRow(
                                totalMinutes = uiState.totalMinutes,
                                totalSessions = uiState.totalSessions,
                                activeDays = uiState.activeDays
                            )
                        }

                        item {
                            ProfileStreakCard(
                                streakDays = uiState.streakDays
                            )
                        }

                        item {
                            ProfileBadgeRow()
                        }

                        item {
                            Text(
                                text = "Últimas sessões",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        items(
                            items = uiState.recentActivities.take(5),
                            key = { activity ->
                                activity.id
                            }
                        ) { activity ->
                            RecentSessionItem(
                                activity = activity
                            )
                        }
                    }
                }
            }
        }
    }
}