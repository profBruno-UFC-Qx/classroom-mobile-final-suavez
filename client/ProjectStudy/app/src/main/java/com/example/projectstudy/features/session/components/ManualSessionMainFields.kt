package com.example.projectstudy.features.session.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.projectstudy.features.session.state.ManualSessionEvent
import com.example.projectstudy.features.session.state.ManualSessionUiState

@Composable
fun ManualSessionMainFields(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit
) {
    ManualSessionSection{
        ManualSessionTextField(
            value = uiState.title,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.TitleChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Título",
            isError = uiState.titleError != null,
            errorMessage = uiState.titleError,
            singleLine = true
        )

        ManualSessionTextField(
            value = uiState.description,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.DescriptionChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Descrição opcional",
            minLines = 4
        )
    }
}