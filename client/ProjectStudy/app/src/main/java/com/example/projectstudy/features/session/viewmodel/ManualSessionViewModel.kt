package com.example.projectstudy.features.session.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.domain.model.CreateManualSessionData
import com.example.projectstudy.domain.usecase.CreateManualSessionUseCase
import com.example.projectstudy.domain.usecase.GetUserGroupsUseCase
import com.example.projectstudy.features.session.state.ManualSessionEvent
import com.example.projectstudy.features.session.state.ManualSessionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ManualSessionViewModel @Inject constructor(
    private val getUserGroupsUseCase: GetUserGroupsUseCase,
    private val createManualSessionUseCase: CreateManualSessionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialGroupId: String? = savedStateHandle["groupId"]

    private val _uiState = MutableStateFlow(ManualSessionUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadGroups()
    }

    fun onEvent(event: ManualSessionEvent) {
        when (event) {

            is ManualSessionEvent.TitleChanged -> {
                _uiState.value = _uiState.value.copy(
                    title = event.value,
                    titleError = null
                )
            }

            is ManualSessionEvent.SubjectChanged -> {
                _uiState.value = _uiState.value.copy(
                    subject = event.value,
                    subjectError = null
                )
            }

            is ManualSessionEvent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(
                    description = event.value
                )
            }

            is ManualSessionEvent.DateChanged -> {
                _uiState.value = _uiState.value.copy(
                    dateMillis = event.millis
                )
            }

            is ManualSessionEvent.StartTimeChanged -> {
                _uiState.value = _uiState.value.copy(
                    startTimeMinutes = event.minutes
                )
            }

            is ManualSessionEvent.DurationChanged -> {
                val filteredValue = event.value
                    .filter { char ->
                        char.isDigit()
                    }

                val durationMinutes = filteredValue.toIntOrNull() ?: 0

                _uiState.value = _uiState.value.copy(
                    durationText = filteredValue,
                    durationMinutes = durationMinutes,
                    durationError = null
                )
            }

            is ManualSessionEvent.MediaSelected -> {
                val current = _uiState.value

                val updatedMedia = (current.selectedMediaUris + event.uris)
                    .distinct()
                    .take(20)

                _uiState.value = current.copy(
                    selectedMediaUris = updatedMedia,
                    mediaError = null
                )
            }

            is ManualSessionEvent.MediaRemoved -> {
                val current = _uiState.value

                _uiState.value = current.copy(
                    selectedMediaUris = current.selectedMediaUris - event.uri
                )
            }

            is ManualSessionEvent.GroupToggled -> {
                toggleGroup(event.groupId)
            }

            ManualSessionEvent.PublishClicked -> {
                publish()
            }

            ManualSessionEvent.PublishedHandled -> {
                _uiState.value = _uiState.value.copy(
                    published = false
                )
            }
        }
    }

    private fun loadGroups() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val groups = getUserGroupsUseCase()

                val selectedIds = when {
                    initialGroupId != null -> {
                        listOf(initialGroupId)
                    }

                    groups.isNotEmpty() -> {
                        listOf(groups.first().id)
                    }

                    else -> {
                        emptyList()
                    }
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    availableGroups = groups,
                    selectedGroupIds = selectedIds
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erro ao carregar grupos"
                )
            }
        }
    }

    private fun toggleGroup(groupId: String) {
        val current = _uiState.value

        val selectedGroups = if (groupId in current.selectedGroupIds) {
            current.selectedGroupIds - groupId
        } else {
            current.selectedGroupIds + groupId
        }

        _uiState.value = current.copy(
            selectedGroupIds = selectedGroups,
            groupError = null
        )
    }

    private fun publish() {
        val current = _uiState.value

        if (!validate(current)) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isPublishing = true,
                error = null
            )

            try {
                createManualSessionUseCase(
                    CreateManualSessionData(
                        title = current.title.trim(),
                        subject = current.subject.trim(),
                        description = current.description.trim(),
                        dateMillis = current.dateMillis,
                        startTimeMinutes = current.startTimeMinutes,
                        durationMinutes = current.durationMinutes,
                        mediaUris = current.selectedMediaUris,
                        groupIds = current.selectedGroupIds
                    )
                )

                _uiState.value = _uiState.value.copy(
                    isPublishing = false,
                    published = true
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isPublishing = false,
                    error = e.message ?: "Erro ao publicar sessão"
                )
            }
        }
    }

    private fun validate(
        state: ManualSessionUiState
    ): Boolean {
        val titleError = if (state.title.isBlank()) {
            "Informe o título"
        } else {
            null
        }

        val subjectError = if (state.subject.isBlank()) {
            "Informe a matéria"
        } else {
            null
        }

        val durationError = if (state.durationMinutes <= 0) {
            "Informe uma duração válida"
        } else {
            null
        }

        val mediaError = if (state.selectedMediaUris.isEmpty()) {
            "Adicione pelo menos uma foto ou vídeo"
        } else {
            null
        }

        val groupError = if (state.selectedGroupIds.isEmpty()) {
            "Selecione pelo menos um grupo"
        } else {
            null
        }

        _uiState.value = state.copy(
            titleError = titleError,
            subjectError = subjectError,
            durationError = durationError,
            mediaError = mediaError,
            groupError = groupError
        )

        return titleError == null &&
                subjectError == null &&
                durationError == null &&
                mediaError == null &&
                groupError == null
    }
}