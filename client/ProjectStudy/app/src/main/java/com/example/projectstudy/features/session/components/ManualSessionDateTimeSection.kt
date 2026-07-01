package com.example.projectstudy.features.session.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projectstudy.core.util.toFeedDateLabel
import com.example.projectstudy.core.util.toTimeText
import com.example.projectstudy.features.session.state.ManualSessionEvent
import com.example.projectstudy.features.session.state.ManualSessionUiState
import com.example.projectstudy.ui.components.LumioTextField
import com.example.projectstudy.ui.components.LumioTextFieldStyle

@Composable
fun ManualSessionDateTimeSection(
    uiState: ManualSessionUiState,
    onEvent: (ManualSessionEvent) -> Unit,
    onDateClick: () -> Unit,
    onStartTimeClick: () -> Unit
) {
    ManualSessionSection {
        LumioTextField(
            value = uiState.subject,
            onValueChange = { value ->
                onEvent(ManualSessionEvent.SubjectChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Matéria",
            isError = uiState.subjectError != null,
            errorMessage = uiState.subjectError,
            singleLine = true,
            style = LumioTextFieldStyle.Embedded
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ManualSessionInfoCard(
                title = "Dia",
                value = uiState.dateMillis.toFeedDateLabel(),
                modifier = Modifier.weight(1f),
                onClick = onDateClick
            )

            ManualSessionInfoCard(
                title = "Início",
                value = uiState.startTimeMinutes.toTimeText(),
                modifier = Modifier.weight(1f),
                onClick = onStartTimeClick
            )
        }

        ManualSessionDurationField(
            durationSeconds = uiState.durationSeconds,
            durationError = uiState.durationError,
            onDurationSelected = { seconds ->
                onEvent(
                    ManualSessionEvent.DurationChanged(seconds)
                )
            }
        )

//        ManualSessionInfoCard(
//            title = "Fim estimado",
//            value = getEstimatedEndTimeText(
//                startTimeMinutes = uiState.startTimeMinutes,
//                durationMinutes = uiState.durationMinutes
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            onClick = null
//        )

    }
}