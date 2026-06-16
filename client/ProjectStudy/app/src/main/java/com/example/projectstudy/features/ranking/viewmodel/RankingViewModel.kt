package com.example.projectstudy.features.ranking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.domain.usecase.GetFirstUserGroupUseCase
import com.example.projectstudy.domain.usecase.GetGroupRankingUseCase
import com.example.projectstudy.features.ranking.state.RankingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getFirstUserGroupUseCase: GetFirstUserGroupUseCase,
    private val getGroupRankingUseCase: GetGroupRankingUseCase
) : ViewModel() {

    val uiState: StateFlow<RankingUiState> =
        getFirstUserGroupUseCase()
            .flatMapLatest { group ->
                getGroupRankingUseCase(group.id)
                    .map { entries ->
                        RankingUiState(
                            isLoading = false,
                            group = group,
                            entries = entries.sortedBy { entry ->
                                entry.position
                            },
                            error = null
                        )
                    }
            }
            .onStart {
                emit(
                    RankingUiState(
                        isLoading = true
                    )
                )
            }
            .catch { exception ->
                emit(
                    RankingUiState(
                        isLoading = false,
                        error = exception.message ?: "Não foi possível carregar o ranking."
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RankingUiState()
            )
}