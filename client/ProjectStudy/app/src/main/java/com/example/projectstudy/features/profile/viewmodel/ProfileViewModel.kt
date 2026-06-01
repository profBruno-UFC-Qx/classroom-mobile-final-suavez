package com.example.projectstudy.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.domain.usecase.GetCurrentUserUseCase
import com.example.projectstudy.domain.usecase.GetUserActivitiesUseCase
import com.example.projectstudy.features.profile.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserActivitiesUseCase: GetUserActivitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val user = getCurrentUserUseCase()

                val recentActivities = getUserActivitiesUseCase(
                    userId = user.id
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    user = user,
                    recentActivities = recentActivities
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erro ao carregar perfil"
                )
            }
        }
    }
}