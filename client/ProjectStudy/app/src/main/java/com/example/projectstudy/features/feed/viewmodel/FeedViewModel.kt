package com.example.projectstudy.features.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getGroupRankingUseCase: GetGroupRankingUseCase
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
                error = null
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

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erro ao carregar feed"
                )
            }
        }
    }
}