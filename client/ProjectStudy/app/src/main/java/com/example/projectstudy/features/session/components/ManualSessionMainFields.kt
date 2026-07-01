package com.example.projectstudy.features.session.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.projectstudy.features.session.state.ManualSessionEvent
import com.example.projectstudy.features.session.state.ManualSessionUiState
import com.example.projectstudy.ui.components.LumioTextField
import com.example.projectstudy.ui.components.LumioTextFieldStyle

@Composable
fun ManualSessionMainFields(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit
) {
    ManualSessionSection{
        LumioTextField(
            value = uiState.title,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.TitleChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Título",
            isError = uiState.titleError != null,
            errorMessage = uiState.titleError,
            singleLine = true,
            style = LumioTextFieldStyle.Embedded
        )

        LumioTextField(
            value = uiState.description,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.DescriptionChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Descrição opcional",
            minLines = 4,
            style = LumioTextFieldStyle.Embedded

        )
    }
}