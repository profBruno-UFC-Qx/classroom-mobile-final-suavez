package com.example.projectstudy.features.session.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectstudy.core.util.toDurationText
import com.example.projectstudy.core.util.toFeedDateLabel
import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.features.session.state.ManualSessionEvent
import com.example.projectstudy.features.session.state.ManualSessionUiState
import com.example.projectstudy.features.session.viewmodel.ManualSessionViewModel
import androidx.compose.foundation.layout.ColumnScope



@Composable
fun ManualSessionScreen(
    onBackClick: () -> Unit,
    onPublished: () -> Unit,
    viewModel: ManualSessionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.published) {
        if (uiState.published) {
            viewModel.onEvent(ManualSessionEvent.PublishedHandled)
            onPublished()
        }
    }

    ManualSessionContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick
    )
}

@Composable
private fun ManualSessionContent(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit,
    onBackClick: () -> Unit
) {
    LazyColumn(
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
                onBackClick = onBackClick
            )
        }

        if (uiState.isLoading) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            item {
                ManualSessionMainFields(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }

            item {
                ManualSessionTimeFields(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }

            item {
                ManualSessionPhotoField(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }

            item {
                ManualSessionGroupSelection(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }

            item {
                Button(
                    onClick = {
                        onEvent(ManualSessionEvent.PublishClicked)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isPublishing
                ) {
                    if (uiState.isPublishing) {
                        CircularProgressIndicator()
                    } else {
                        Text(text = "Publicar no feed")
                    }
                }
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

@Composable
private fun ManualSessionTopBar(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Voltar"
            )
        }

        Text(
            text = "Nova sessão",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ManualSessionMainFields(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit
) {
    ManualSessionSection(
        title = "Dados do estudo"
    ) {
        OutlinedTextField(
            value = uiState.title,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.TitleChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Título")
            },
            isError = uiState.titleError != null,
            supportingText = {
                uiState.titleError?.let { error ->
                    Text(text = error)
                }
            },
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.subject,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.SubjectChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Matéria")
            },
            isError = uiState.subjectError != null,
            supportingText = {
                uiState.subjectError?.let { error ->
                    Text(text = error)
                }
            },
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.description,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.DescriptionChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Descrição opcional")
            },
            minLines = 3
        )
    }
}

@Composable
private fun ManualSessionTimeFields(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit
) {
    ManualSessionSection(
        title = "Tempo"
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = uiState.startHour,
                onValueChange = { value ->
                    onEvent(ManualSessionEvent.StartHourChanged(value))
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text(text = "Hora início")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.startMinute,
                onValueChange = { value ->
                    onEvent(ManualSessionEvent.StartMinuteChanged(value))
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text(text = "Min início")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = uiState.endHour,
                onValueChange = { value ->
                    onEvent(ManualSessionEvent.EndHourChanged(value))
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text(text = "Hora fim")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.endMinute,
                onValueChange = { value ->
                    onEvent(ManualSessionEvent.EndMinuteChanged(value))
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text(text = "Min fim")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
        }

        Text(
            text = "Duração calculada: ${uiState.durationMinutes.toDurationText()}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        uiState.timeError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun ManualSessionPhotoField(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit
) {
    ManualSessionSection(
        title = "Foto"
    ) {
        OutlinedTextField(
            value = uiState.imageUrl,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.ImageUrlChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "URL da foto")
            },
            isError = uiState.imageError != null,
            supportingText = {
                uiState.imageError?.let { error ->
                    Text(text = error)
                }
            },
            singleLine = true
        )
    }
}

@Composable
private fun ManualSessionGroupSelection(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit
) {
    ManualSessionSection(
        title = "Publicar em"
    ) {
        uiState.availableGroups.forEach { group ->
            GroupSelectionItem(
                group = group,
                selected = group.id in uiState.selectedGroupIds,
                onClick = {
                    onEvent(ManualSessionEvent.GroupToggled(group.id))
                }
            )
        }

        uiState.groupError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun GroupSelectionItem(
    group: Group,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selected,
                onCheckedChange = {
                    onClick()
                }
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${group.memberCount} membros",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun ManualSessionSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            content()
        }
    }
}