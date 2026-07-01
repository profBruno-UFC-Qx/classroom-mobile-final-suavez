package com.example.projectstudy.features.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.data.repository.AuthRepository
import com.example.projectstudy.data.sync.RemoteSyncService
import com.example.projectstudy.domain.model.NoUserGroupException
import com.example.projectstudy.domain.usecase.GetFirstUserGroupUseCase
import com.example.projectstudy.domain.usecase.GetGroupActivitiesUseCase
import com.example.projectstudy.domain.usecase.GetGroupRankingUseCase
import com.example.projectstudy.features.feed.state.FeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFirstUserGroupUseCase: GetFirstUserGroupUseCase,
    private val getGroupActivitiesUseCase: GetGroupActivitiesUseCase,
    private val getGroupRankingUseCase: GetGroupRankingUseCase,
    private val authRepository: AuthRepository,
    private val remoteSyncService: RemoteSyncService
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    private var loadFeedJob: Job? = null

    init {
        loadFeed()
    }

    fun loadFeed() {
        loadFeedJob?.cancel()

        loadFeedJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                hasNoGroup = false
            )

            try {
                getFirstUserGroupUseCase()
                    .flatMapLatest { group ->
                        combine(
                            getGroupActivitiesUseCase(group.id),
                            getGroupRankingUseCase(group.id)
                        ) { activities, ranking ->
                            FeedUiState(
                                isLoading = false,
                                isRefreshing = _uiState.value.isRefreshing,
                                group = group,
                                activities = activities,
                                ranking = ranking,
                                error = null
                            )
                        }
                    }
                    .collectLatest { newState ->
                        _uiState.value = newState
                    }

            } catch (e: NoUserGroupException) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    hasNoGroup = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    error = e.message ?: "Erro ao carregar feed"
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
                    error = error.message ?: "Erro ao atualizar feed"
                )
            }

            _uiState.value = _uiState.value.copy(
                isRefreshing = false
            )
        }
    }
}