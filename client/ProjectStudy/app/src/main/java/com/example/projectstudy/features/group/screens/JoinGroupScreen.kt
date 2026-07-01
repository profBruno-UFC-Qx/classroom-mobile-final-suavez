package com.example.projectstudy.features.group.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectstudy.features.group.state.JoinGroupEvent
import com.example.projectstudy.features.group.viewmodel.JoinGroupViewModel
import com.example.projectstudy.ui.components.LumioTextField
import com.example.projectstudy.ui.components.LumioTextFieldStyle

@Composable
fun JoinGroupScreen(
    onJoinSuccess: () -> Unit,
    viewModel: JoinGroupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.joined) {
        if (uiState.joined) {
            viewModel.onEvent(JoinGroupEvent.JoinHandled)
            onJoinSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(
                PaddingValues(
                    horizontal = 24.dp,
                    vertical = 28.dp
                )
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Entrar em um grupo",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Você ainda não faz parte de nenhum grupo. " +
                "Peça o código de convite para quem administra o grupo.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(36.dp))

        LumioTextField(
            value = uiState.inviteCode,
            onValueChange = { value ->
                viewModel.onEvent(
                    JoinGroupEvent.InviteCodeChanged(value)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Código do grupo",
            leadingIcon = Icons.Outlined.Groups,
            errorMessage = uiState.inviteCodeError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters
            ),
            style = LumioTextFieldStyle.Standalone
        )

        uiState.error?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.onEvent(JoinGroupEvent.JoinClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Entrar no grupo",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
