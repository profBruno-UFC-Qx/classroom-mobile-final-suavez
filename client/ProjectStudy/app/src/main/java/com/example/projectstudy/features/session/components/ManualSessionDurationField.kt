package com.example.projectstudy.features.session.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ManualSessionDurationField(
    durationText: String,
    durationMinutes: Int,
    durationError: String?,
    onDurationSelected: (String) -> Unit
) {
    Column{
        ManualSessionTextField(
            value = durationText,
            onValueChange = { value ->
                onDurationSelected(value)},
            modifier = Modifier.fillMaxWidth(),
            label = "Duração em minutos",
            isError = durationError != null,
            errorMessage = durationError,
            singleLine = true
        )
        if (durationMinutes > 0) {
            Text(
                text = "Tempo total: ${durationMinutes.toDurationPreview()}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

private fun Int.toDurationPreview(): String {
    val hours = this / 60
    val minutes = this % 60

    return when {
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}min"
        hours > 0 -> "${hours}h"
        else -> "${minutes}min"
    }
}