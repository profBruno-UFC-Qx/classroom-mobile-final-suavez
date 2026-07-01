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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val joinGroupUseCase: JoinGroupUseCase,
    private val authRepository: AuthRepository,
    private val remoteSyncService: RemoteSyncService
) : ViewModel() {

    private val _uiState = MutableStateFlow(JoinGroupUiState())
    val uiState = _uiState.asStateFlow()

    private val _joinSuccessEvents = Channel<Unit>(Channel.BUFFERED)
    val joinSuccessEvents = _joinSuccessEvents.receiveAsFlow()

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
                        error = null
                    )

                    _joinSuccessEvents.send(Unit)
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = throwable.message
                            ?: "Não foi possível entrar no grupo"
                    )
                }
        }
    }
}
