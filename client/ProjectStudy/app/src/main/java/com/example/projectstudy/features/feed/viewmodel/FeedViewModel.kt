package com.example.projectstudy.features.feed.viewmodel

import android.util.Printer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.domain.usecase.GetFirstUserGroupUseCase
import com.example.projectstudy.domain.usecase.GetGroupActivitiesUseCase
import com.example.projectstudy.domain.usecase.GetGroupRankingUseCase
import com.example.projectstudy.features.feed.state.FeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFirstUserGroupUseCase: GetFirstUserGroupUseCase,
    private val getGroupActivitiesUseCase: GetGroupActivitiesUseCase,
    private val getGroupRankingUseCase: GetGroupRankingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()


    init {
        loadFeed()
    }

    fun loadFeed() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {

                val group = getFirstUserGroupUseCase()

                val ranking = getGroupRankingUseCase(
                    groupId = group.id
                )

                val activities = getGroupActivitiesUseCase(
                    groupId = group.id
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    group = group,
                    ranking = ranking,
                    activities = activities
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erro ao carregar feed"
                )

            }
        }
    }
}