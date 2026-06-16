package com.example.projectstudy.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.features.auth.state.RegisterEvent
import com.example.projectstudy.features.auth.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NameChanged -> {
                _uiState.value = _uiState.value.copy(
                    name = event.value,
                    nameError = null
                )
            }

            is RegisterEvent.UsernameChanged -> {
                _uiState.value = _uiState.value.copy(
                    username = event.value,
                    usernameError = null
                )
            }

            is RegisterEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = event.value,
                    emailError = null
                )
            }

            is RegisterEvent.InstitutionChanged -> {
                _uiState.value = _uiState.value.copy(
                    institution = event.value
                )
            }

            is RegisterEvent.CourseChanged -> {
                _uiState.value = _uiState.value.copy(
                    course = event.value
                )
            }

            is RegisterEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = event.value,
                    passwordError = null
                )
            }

            is RegisterEvent.ConfirmPasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    confirmPassword = event.value,
                    confirmPasswordError = null
                )
            }

            RegisterEvent.RegisterClicked -> {
                register()
            }

            RegisterEvent.RegisterHandled -> {
                _uiState.value = _uiState.value.copy(
                    registered = false
                )
            }
        }
    }

    private fun register() {
        val current = _uiState.value

        if (!validate(current)) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            delay(500)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                registered = true
            )
        }
    }

    private fun validate(
        state: RegisterUiState
    ): Boolean {
        val nameError = if (state.name.isBlank()) {
            "Informe seu nome"
        } else {
            null
        }

        val usernameError = if (state.username.isBlank()) {
            "Informe seu username"
        } else if (!state.username.startsWith("@")) {
            "O username deve começar com @"
        } else {
            null
        }

        val emailError = if (state.email.isBlank()) {
            "Informe seu email"
        } else if (!state.email.contains("@")) {
            "Informe um email válido"
        } else {
            null
        }

        val passwordError = if (state.password.isBlank()) {
            "Informe sua senha"
        } else if (state.password.length < 8) {
            "A senha precisa ter pelo menos 8 caracteres"
        } else {
            null
        }

        val confirmPasswordError = if (state.confirmPassword.isBlank()) {
            "Confirme sua senha"
        } else if (state.confirmPassword != state.password) {
            "As senhas não conferem"
        } else {
            null
        }

        _uiState.value = state.copy(
            nameError = nameError,
            usernameError = usernameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        )

        return nameError == null &&
                usernameError == null &&
                emailError == null &&
                passwordError == null &&
                confirmPasswordError == null
    }
}