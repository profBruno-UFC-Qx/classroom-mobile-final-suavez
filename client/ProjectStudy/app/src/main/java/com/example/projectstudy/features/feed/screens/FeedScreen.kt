package com.example.projectstudy.features.feed.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectstudy.features.feed.components.ActivityCard
import com.example.projectstudy.features.feed.components.GroupBanner
import com.example.projectstudy.features.feed.viewmodel.FeedViewModel
import com.example.projectstudy.features.feed.components.RankingSummaryCard
import com.example.projectstudy.core.util.toFeedDateLabel
import com.example.projectstudy.features.feed.components.FeedDateHeader

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.projectstudy.navigation.MainBottomTab
import com.example.projectstudy.ui.components.LumioBottomBar

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {

                uiState.isLoading -> {

                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                }

                else -> {

                    val groupedActivities = uiState.activities
                        .sortedByDescending { activity ->
                            activity.createdAtMillis
                        }
                        .groupBy { activity ->
                            activity.createdAtMillis.toFeedDateLabel()
                        }

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

                            Text(
                                text = "Feed",
                                style = MaterialTheme.typography.titleLarge
                            )

                        }

                        uiState.group?.let { group ->

                            item {
                                GroupBanner(
                                    group = group
                                )
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
                                FeedDateHeader(
                                    label = dateLabel
                                )
                            }

                            items(
                                items = activities,
                                key = { activity -> activity.id }
                            ) { activity ->
                                ActivityCard(
                                    activity = activity
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}