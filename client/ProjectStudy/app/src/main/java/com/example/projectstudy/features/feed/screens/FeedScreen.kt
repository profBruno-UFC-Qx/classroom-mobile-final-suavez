package com.example.projectstudy.features.feed.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectstudy.core.util.toFeedDateLabel
import com.example.projectstudy.features.feed.components.ActivityCard
import com.example.projectstudy.features.feed.components.FeedDateHeader
import com.example.projectstudy.features.feed.components.GroupBanner
import com.example.projectstudy.features.feed.components.RankingSummaryCard
import com.example.projectstudy.features.feed.viewmodel.FeedViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold

@Composable
fun FeedScreen(
    onAddSessionClick: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            if (!uiState.isLoading && uiState.error == null) {
                uiState.group?.let { group ->
                    FloatingActionButton(
                        onClick = {
                            onAddSessionClick(group.id)
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Adicionar sessão"
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.error != null -> {
                    Text(
                        text = uiState.error ?: "Erro ao carregar feed",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    val groupedActivities = uiState.activities
                        .sortedByDescending { activity ->
                            activity.startedAtMillis
                        }
                        .groupBy { activity ->
                            activity.startedAtMillis.toFeedDateLabel()
                        }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding(),

                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 12.dp,
                            end = 16.dp,
                            bottom = 80.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                text = "Feed",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        uiState.group?.let { group ->
                            item {
                                GroupBanner(group = group)
                            }

                            item {
                                RankingSummaryCard(
                                    group = group,
                                    ranking = uiState.ranking
                                )
                            }
                        }

                        groupedActivities.forEach { (dateLabel, activities) ->
                            item {
                                FeedDateHeader(label = dateLabel)
                            }

                            items(
                                items = activities,
                                key = { activity -> activity.id }
                            ) { activity ->
                                ActivityCard(activity = activity)
                            }
                        }
                    }
                }
            }
        }
    }
}