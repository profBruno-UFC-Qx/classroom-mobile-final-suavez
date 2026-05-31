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
import com.example.projectstudy.features.feed.components.GroupBanner
import com.example.projectstudy.features.feed.components.RankingSummaryCard

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold {

            padding ->

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

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
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

                        items(uiState.activities) {

                                activity ->

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