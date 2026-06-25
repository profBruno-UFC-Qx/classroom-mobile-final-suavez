package com.example.projectstudy.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.data.repository.AuthRepository
import com.example.projectstudy.features.auth.state.RegisterEvent
import com.example.projectstudy.features.auth.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NameChanged -> {
                _uiState.value = _uiState.value.copy(
                    name = event.value,
                    nameError = null,
                    error = null
                )
            }

            is RegisterEvent.UsernameChanged -> {
                _uiState.value = _uiState.value.copy(
                    username = event.value,
                    usernameError = null,
                    error = null
                )
            }

            is RegisterEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = event.value,
                    emailError = null,
                    error = null
                )
            }

            is RegisterEvent.InstitutionChanged -> {
                _uiState.value = _uiState.value.copy(
                    institution = event.value,
                    error = null
                )
            }

            is RegisterEvent.CourseChanged -> {
                _uiState.value = _uiState.value.copy(
                    course = event.value,
                    error = null
                )
            }

            is RegisterEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = event.value,
                    passwordError = null,
                    error = null
                )
            }

            is RegisterEvent.ConfirmPasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    confirmPassword = event.value,
                    confirmPasswordError = null,
                    error = null
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

            val result = authRepository.register(
                name = current.name.trim(),
                username = normalizeUsername(current.username),
                email = current.email.trim(),
                password = current.password,
                institution = current.institution.trim(),
                course = current.course.trim()
            )

            result
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        registered = true,
                        error = null
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        registered = false,
                        error = throwable.message
                            ?: "Não foi possível criar a conta"
                    )
                }
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
        } else if (state.password.length < 6) {
            "A senha precisa ter pelo menos 6 caracteres"
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

    private fun normalizeUsername(username: String): String {
        return username
            .trim()
            .removePrefix("@")
    }
}