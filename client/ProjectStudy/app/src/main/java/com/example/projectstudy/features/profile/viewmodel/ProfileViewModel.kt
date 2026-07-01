package com.example.projectstudy.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.data.repository.AuthRepository
import com.example.projectstudy.data.sync.RemoteSyncService
import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.usecase.GetCurrentUserUseCase
import com.example.projectstudy.domain.usecase.GetUserActivitiesUseCase
import com.example.projectstudy.features.profile.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserActivitiesUseCase: GetUserActivitiesUseCase,
    private val authRepository: AuthRepository,
    private val remoteSyncService: RemoteSyncService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private var loadProfileJob: Job? = null

    init {
        loadProfile()
    }

    fun loadProfile() {
        loadProfileJob?.cancel()

        loadProfileJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val user = getCurrentUserUseCase()

                _uiState.value = _uiState.value.copy(
                    user = user
                )

                getUserActivitiesUseCase(
                    userId = user.id
                ).collectLatest { recentActivities ->
                    val totalMinutes = recentActivities.sumOf { activity ->
                        activity.durationMinutes
                    }

                    val totalSessions = recentActivities.size

                    val activeDays = calculateActiveDays(
                        activities = recentActivities
                    )

                    val streakDays = calculateStreakDays(
                        activities = recentActivities
                    )

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        user = user,
                        recentActivities = recentActivities,
                        totalMinutes = totalMinutes,
                        totalSessions = totalSessions,
                        activeDays = activeDays,
                        streakDays = streakDays,
                        error = null
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    error = e.message ?: "Erro ao carregar perfil"
                )
            }
        }
    }

    fun refreshRemoteData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isRefreshing = true,
                error = null
            )

            val token = authRepository.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    error = "Usuário não autenticado"
                )
                return@launch
            }

            runCatching {
                remoteSyncService.pullAndSave(
                    accessToken = token
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.message ?: "Erro ao atualizar perfil"
                )
            }

            _uiState.value = _uiState.value.copy(
                isRefreshing = false
            )
        }
    }

    private fun calculateActiveDays(
        activities: List<StudyActivity>
    ): Int {
        return activities
            .map { activity ->
                millisToLocalDate(activity.startedAtMillis)
            }
            .distinct()
            .size
    }

    private fun calculateStreakDays(
        activities: List<StudyActivity>
    ): Int {
        val activityDays = activities
            .map { activity ->
                millisToLocalDate(activity.startedAtMillis)
            }
            .toSet()

        if (activityDays.isEmpty()) {
            return 0
        }

        var currentDate = LocalDate.now()
        var streak = 0

        while (activityDays.contains(currentDate)) {
            streak++
            currentDate = currentDate.minusDays(1)
        }

        return streak
    }

    private fun millisToLocalDate(
        millis: Long
    ): LocalDate {
        return Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}