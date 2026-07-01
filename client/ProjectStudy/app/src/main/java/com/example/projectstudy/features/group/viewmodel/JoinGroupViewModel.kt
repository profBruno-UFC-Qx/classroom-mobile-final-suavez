package com.example.projectstudy.features.group.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.data.repository.AuthRepository
import com.example.projectstudy.data.sync.RemoteSyncService
import com.example.projectstudy.domain.usecase.JoinGroupUseCase
import com.example.projectstudy.features.group.state.JoinGroupEvent
import com.example.projectstudy.features.group.state.JoinGroupUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val joinGroupUseCase: JoinGroupUseCase,
    private val authRepository: AuthRepository,
    private val remoteSyncService: RemoteSyncService
) : ViewModel() {

    private val _uiState = MutableStateFlow(JoinGroupUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: JoinGroupEvent) {
        when (event) {
            is JoinGroupEvent.InviteCodeChanged -> {
                _uiState.value = _uiState.value.copy(
                    inviteCode = event.value,
                    inviteCodeError = null,
                    error = null
                )
            }

            JoinGroupEvent.JoinClicked -> {
                joinGroup()
            }

            JoinGroupEvent.JoinHandled -> {
                _uiState.value = _uiState.value.copy(
                    joined = false
                )
            }
        }
    }

    private fun joinGroup() {
        val current = _uiState.value

        if (current.isLoading) {
            return
        }

        val inviteCode = current.inviteCode.trim()

        if (inviteCode.isBlank()) {
            _uiState.value = current.copy(
                inviteCodeError = "Informe o código do grupo"
            )
            return
        }

        _uiState.value = current.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            val token = authRepository.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Usuário não autenticado"
                )
                return@launch
            }

            joinGroupUseCase(
                token = token,
                inviteCode = inviteCode
            )
                .onSuccess {
                    runCatching {
                        remoteSyncService.pullAndSave(
                            accessToken = token
                        )
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        joined = true,
                        error = null
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        joined = false,
                        error = throwable.message
                            ?: "Não foi possível entrar no grupo"
                    )
                }
        }
    }
}
