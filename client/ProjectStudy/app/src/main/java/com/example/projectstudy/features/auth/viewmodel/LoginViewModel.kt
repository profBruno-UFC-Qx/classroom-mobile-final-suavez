package com.example.projectstudy.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.features.auth.state.LoginEvent
import com.example.projectstudy.features.auth.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailOrUsernameChanged -> {
                _uiState.value = _uiState.value.copy(
                    emailOrUsername = event.value,
                    emailOrUsernameError = null
                )
            }

            is LoginEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = event.value,
                    passwordError = null
                )
            }

            LoginEvent.LoginClicked -> {
                login()
            }

            LoginEvent.LoginHandled -> {
                _uiState.value = _uiState.value.copy(
                    loggedIn = false
                )
            }
        }
    }

    private fun login() {
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
                loggedIn = true
            )
        }
    }

    private fun validate(
        state: LoginUiState
    ): Boolean {
        val emailOrUsernameError = if (state.emailOrUsername.isBlank()) {
            "Informe seu email ou username"
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

        _uiState.value = state.copy(
            emailOrUsernameError = emailOrUsernameError,
            passwordError = passwordError
        )

        return emailOrUsernameError == null &&
                passwordError == null
    }
}