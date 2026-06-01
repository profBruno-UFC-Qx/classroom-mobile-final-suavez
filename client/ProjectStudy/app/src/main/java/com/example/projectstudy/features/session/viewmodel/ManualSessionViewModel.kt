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

            is ManualSessionEvent.StartHourChanged -> {
                _uiState.value = _uiState.value.copy(
                    startHour = event.value.filter { char ->
                        char.isDigit()
                    }.take(2),
                    timeError = null
                )

                updateDuration()
            }

            is ManualSessionEvent.StartMinuteChanged -> {
                _uiState.value = _uiState.value.copy(
                    startMinute = event.value.filter { char ->
                        char.isDigit()
                    }.take(2),
                    timeError = null
                )

                updateDuration()
            }

            is ManualSessionEvent.EndHourChanged -> {
                _uiState.value = _uiState.value.copy(
                    endHour = event.value.filter { char ->
                        char.isDigit()
                    }.take(2),
                    timeError = null
                )

                updateDuration()
            }

            is ManualSessionEvent.EndMinuteChanged -> {
                _uiState.value = _uiState.value.copy(
                    endMinute = event.value.filter { char ->
                        char.isDigit()
                    }.take(2),
                    timeError = null
                )

                updateDuration()
            }

            is ManualSessionEvent.ImageUrlChanged -> {
                _uiState.value = _uiState.value.copy(
                    imageUrl = event.value,
                    imageError = null
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

    private fun updateDuration() {
        val current = _uiState.value

        val start = current.startTimeInMinutesOrNull()
        val end = current.endTimeInMinutesOrNull()

        val duration = if (start != null && end != null && end > start) {
            end - start
        } else {
            0
        }

        _uiState.value = current.copy(
            durationMinutes = duration
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
                        durationMinutes = current.durationMinutes,
                        imageUrl = current.imageUrl.trim(),
                        dateMillis = current.dateMillis,
                        startTimeMinutes = current.startTimeInMinutesOrNull() ?: 0,
                        endTimeMinutes = current.endTimeInMinutesOrNull() ?: 0,
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

        val imageError = if (state.imageUrl.isBlank()) {
            "Adicione uma foto ou URL"
        } else {
            null
        }

        val groupError = if (state.selectedGroupIds.isEmpty()) {
            "Selecione pelo menos um grupo"
        } else {
            null
        }

        val timeError = when {
            state.startTimeInMinutesOrNull() == null ||
                    state.endTimeInMinutesOrNull() == null -> {
                "Informe início e fim"
            }

            state.durationMinutes <= 0 -> {
                "O horário final deve ser maior que o inicial"
            }

            else -> {
                null
            }
        }

        _uiState.value = state.copy(
            titleError = titleError,
            subjectError = subjectError,
            imageError = imageError,
            groupError = groupError,
            timeError = timeError
        )

        return titleError == null &&
                subjectError == null &&
                imageError == null &&
                groupError == null &&
                timeError == null
    }

    private fun ManualSessionUiState.startTimeInMinutesOrNull(): Int? {
        return timeInMinutesOrNull(
            hour = startHour,
            minute = startMinute
        )
    }

    private fun ManualSessionUiState.endTimeInMinutesOrNull(): Int? {
        return timeInMinutesOrNull(
            hour = endHour,
            minute = endMinute
        )
    }

    private fun timeInMinutesOrNull(
        hour: String,
        minute: String
    ): Int? {
        val parsedHour = hour.toIntOrNull()
        val parsedMinute = minute.toIntOrNull()

        if (parsedHour == null || parsedMinute == null) {
            return null
        }

        if (parsedHour !in 0..23 || parsedMinute !in 0..59) {
            return null
        }

        return parsedHour * 60 + parsedMinute
    }
}