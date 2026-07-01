package com.example.projectstudy.features.auth.screens

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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectstudy.features.auth.state.RegisterEvent
import com.example.projectstudy.features.auth.viewmodel.RegisterViewModel
import com.example.projectstudy.ui.components.LumioTextField
import com.example.projectstudy.ui.components.LumioTextFieldStyle

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.registered) {
        if (uiState.registered) {
            viewModel.onEvent(RegisterEvent.RegisterHandled)
            onRegisterSuccess()
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
            text = "Criar conta",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Entre no Lumio e acompanhe seus estudos.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        LumioTextField(
            value = uiState.name,
            onValueChange = { value ->
                viewModel.onEvent(RegisterEvent.NameChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Nome",
            leadingIcon = Icons.Outlined.Person,
            errorMessage = uiState.nameError,
            singleLine = true,
            style = LumioTextFieldStyle.Standalone
        )

        Spacer(modifier = Modifier.height(10.dp))

        LumioTextField(
            value = uiState.username,
            onValueChange = { value ->
                viewModel.onEvent(RegisterEvent.UsernameChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Username",
            leadingIcon = Icons.Outlined.Person,
            errorMessage = uiState.usernameError,
            singleLine = true,
            style = LumioTextFieldStyle.Standalone
        )

        Spacer(modifier = Modifier.height(10.dp))

        LumioTextField(
            value = uiState.email,
            onValueChange = { value ->
                viewModel.onEvent(RegisterEvent.EmailChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Email",
            leadingIcon = Icons.Outlined.Email,
            errorMessage = uiState.emailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            style = LumioTextFieldStyle.Standalone
        )

        Spacer(modifier = Modifier.height(10.dp))

        LumioTextField(
            value = uiState.institution,
            onValueChange = { value ->
                viewModel.onEvent(RegisterEvent.InstitutionChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Instituição",
            leadingIcon = Icons.Outlined.School,
            singleLine = true,
            style = LumioTextFieldStyle.Standalone
        )

        Spacer(modifier = Modifier.height(10.dp))

        LumioTextField(
            value = uiState.course,
            onValueChange = { value ->
                viewModel.onEvent(RegisterEvent.CourseChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Curso",
            leadingIcon = Icons.Outlined.School,
            singleLine = true,
            style = LumioTextFieldStyle.Standalone
        )

        Spacer(modifier = Modifier.height(10.dp))

        LumioTextField(
            value = uiState.password,
            onValueChange = { value ->
                viewModel.onEvent(RegisterEvent.PasswordChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Senha",
            leadingIcon = Icons.Outlined.Lock,
            errorMessage = uiState.passwordError,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            style = LumioTextFieldStyle.Standalone
        )

        Spacer(modifier = Modifier.height(10.dp))

        LumioTextField(
            value = uiState.confirmPassword,
            onValueChange = { value ->
                viewModel.onEvent(RegisterEvent.ConfirmPasswordChanged(value))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Confirmar senha",
            leadingIcon = Icons.Outlined.Lock,
            errorMessage = uiState.confirmPasswordError,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
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
                viewModel.onEvent(RegisterEvent.RegisterClicked)
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
                    text = "Criar conta",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Já tenho conta")
        }
    }
}