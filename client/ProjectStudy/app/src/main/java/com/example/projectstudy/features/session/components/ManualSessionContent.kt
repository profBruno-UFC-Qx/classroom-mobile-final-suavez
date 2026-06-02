package com.example.projectstudy.features.session.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projectstudy.features.session.state.ManualSessionEvent
import com.example.projectstudy.features.session.state.ManualSessionUiState

@Composable
fun ManualSessionContent(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit,
    onBackClick: () -> Unit,
    onPublishClick: () -> Unit,
    onPickMediaClick: () -> Unit,
    onDateClick: () -> Unit,
    onStartTimeClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 12.dp,
            end = 16.dp,
            bottom = 110.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ManualSessionTopBar(
                isPublishing = uiState.isPublishing,
                onBackClick = onBackClick,
                onPublishClick = onPublishClick
            )
        }

        if (uiState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            item {
                ManualSessionMediaPicker(
                    uiState = uiState,
                    onPickMediaClick = onPickMediaClick,
                    onRemoveMedia = { uri ->
                        onEvent(
                            ManualSessionEvent.MediaRemoved(uri)
                        )
                    }
                )
            }

            item {
                ManualSessionMainFields(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }

            item {
                ManualSessionDateTimeSection(
                    uiState = uiState,
                    onEvent = onEvent,
                    onDateClick = onDateClick,
                    onStartTimeClick = onStartTimeClick
                )
            }

            item {
                ManualSessionGroupSelection(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }

            uiState.error?.let { error ->
                item {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}