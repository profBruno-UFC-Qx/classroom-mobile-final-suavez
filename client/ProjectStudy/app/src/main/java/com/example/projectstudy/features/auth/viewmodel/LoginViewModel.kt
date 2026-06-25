package com.example.projectstudy.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.data.repository.AuthRepository
import com.example.projectstudy.features.auth.state.LoginEvent
import com.example.projectstudy.features.auth.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailOrUsernameChanged -> {
                _uiState.value = _uiState.value.copy(
                    emailOrUsername = event.value,
                    emailOrUsernameError = null,
                    error = null
                )
            }

            is LoginEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = event.value,
                    passwordError = null,
                    error = null
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

            val username = normalizeEmailOrUsername(
                value = current.emailOrUsername
            )

            val result = authRepository.login(
                username = username,
                password = current.password
            )

            result
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loggedIn = true,
                        error = null
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loggedIn = false,
                        error = throwable.message
                            ?: "Não foi possível fazer login"
                    )
                }
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
        } else if (state.password.length < 6) {
            "A senha precisa ter pelo menos 6 caracteres"
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

    private fun normalizeEmailOrUsername(value: String): String {
        val trimmed = value.trim()

        return if (trimmed.startsWith("@")) {
            trimmed.removePrefix("@")
        } else {
            trimmed
        }
    }
}
