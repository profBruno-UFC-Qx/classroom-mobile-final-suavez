package com.example.projectstudy.features.session.components

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ManualSessionDurationField(
    durationSeconds: Int,
    durationError: String?,
    onDurationSelected: (Int) -> Unit
) {
    var showPicker by remember {
        mutableStateOf(false)
    }

    var selectedHours by remember(durationSeconds) {
        mutableIntStateOf(durationSeconds / 3600)
    }

    var selectedMinutes by remember(durationSeconds) {
        mutableIntStateOf((durationSeconds % 3600) / 60)
    }

    var selectedSeconds by remember(durationSeconds) {
        mutableIntStateOf(durationSeconds % 60)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ManualSessionInfoCard(
            title = "Duração",
            value = if (durationSeconds > 0) {
                durationSeconds.toDurationPreview()
            } else {
                "Selecionar duração"
            },
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                selectedHours = durationSeconds / 3600
                selectedMinutes = (durationSeconds % 3600) / 60
                selectedSeconds = durationSeconds % 60
                showPicker = true
            }
        )

        durationError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }

    if (showPicker) {
        AlertDialog(
            onDismissRequest = {
                showPicker = false
            },
            title = {
                Text(
                    text = "Selecionar duração",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DurationNumberPicker(
                        value = selectedHours,
                        range = 0..20,
                        suffix = "h",
                        onValueChange = { value ->
                            selectedHours = value
                        }
                    )

                    DurationNumberPicker(
                        value = selectedMinutes,
                        range = 0..59,
                        suffix = "m",
                        onValueChange = { value ->
                            selectedMinutes = value
                        }
                    )

                    DurationNumberPicker(
                        value = selectedSeconds,
                        range = 0..59,
                        suffix = "s",
                        onValueChange = { value ->
                            selectedSeconds = value
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val totalSeconds =
                            selectedHours * 3600 +
                                    selectedMinutes * 60 +
                                    selectedSeconds

                        onDurationSelected(totalSeconds)

                        showPicker = false
                    }
                ) {
                    Text(
                        text = "OK",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showPicker = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}

@Composable
private fun DurationNumberPicker(
    value: Int,
    range: IntRange,
    suffix: String,
    onValueChange: (Int) -> Unit
) {
    AndroidView(
        modifier = Modifier
            .width(92.dp)
            .height(140.dp),
        factory = { context ->
            NumberPicker(context).apply {
                minValue = range.first
                maxValue = range.last
                wrapSelectorWheel = true

                displayedValues = range.map { number ->
                    "$number $suffix"
                }.toTypedArray()

                setOnValueChangedListener { _, _, newValue ->
                    onValueChange(newValue)
                }
            }
        },
        update = { picker ->
            picker.minValue = range.first
            picker.maxValue = range.last
            picker.value = value
        }
    )
}

private fun Int.toDurationPreview(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return when {
        hours > 0 && minutes > 0 && seconds > 0 -> "${hours}h ${minutes}min ${seconds}s"
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}min"
        hours > 0 && seconds > 0 -> "${hours}h ${seconds}s"
        hours > 0 -> "${hours}h"

        minutes > 0 && seconds > 0 -> "${minutes}min ${seconds}s"
        minutes > 0 -> "${minutes}min"

        else -> "${seconds}s"
    }
}