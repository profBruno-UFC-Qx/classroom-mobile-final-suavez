package com.example.projectstudy.features.session.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectstudy.features.session.components.ManualSessionContent
import com.example.projectstudy.features.session.state.ManualSessionEvent
import com.example.projectstudy.features.session.viewmodel.ManualSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualSessionScreen(
    onBackClick: () -> Unit,
    onPublished: () -> Unit,
    viewModel: ManualSessionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var showStartTimePicker by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.loadGroups()
    }

    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = 20
        )
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.onEvent(
                ManualSessionEvent.MediaSelected(
                    uris = uris.map { uri ->
                        uri.toString()
                    }
                )
            )
        }
    }

    LaunchedEffect(uiState.published) {
        if (uiState.published) {
            viewModel.onEvent(ManualSessionEvent.PublishedHandled)
            onPublished()
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = uiState.dateMillis
        )

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            viewModel.onEvent(
                                ManualSessionEvent.DateChanged(millis)
                            )
                        }

                        showDatePicker = false
                    }
                ) {
                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    if (showStartTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = uiState.startTimeMinutes / 60,
            initialMinute = uiState.startTimeMinutes % 60,
            is24Hour = true
        )

        AlertDialog(
            onDismissRequest = {
                showStartTimePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val minutes = timePickerState.hour * 60 +
                                timePickerState.minute

                        viewModel.onEvent(
                            ManualSessionEvent.StartTimeChanged(minutes)
                        )

                        showStartTimePicker = false
                    }
                ) {
                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showStartTimePicker = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TimePicker(
                        state = timePickerState
                    )
                }
            }
        )
    }

    ManualSessionContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick,
        onPublishClick = {
            viewModel.onEvent(ManualSessionEvent.PublishClicked)
        },
        onPickMediaClick = {
            mediaPickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageAndVideo
                )
            )
        },
        onDateClick = {
            showDatePicker = true
        },
        onStartTimeClick = {
            showStartTimePicker = true
        }
    )
}