package com.example.projectstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectstudy.domain.model.Activity
import com.example.projectstudy.domain.repository.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // 1. Avisa o Hilt que isso é um ViewModel
class FeedViewModel @Inject constructor(
    private val repository: ActivityRepository // 2. O Hilt injeta a interface aqui!
) : ViewModel() {

    private val _feedState = MutableStateFlow<List<Activity>>(emptyList())
    val feedState: StateFlow<List<Activity>> = _feedState.asStateFlow()

    init {
        loadFeed()
    }

    private fun loadFeed() {
        viewModelScope.launch {
            _feedState.value = repository.getFeed()
        }
    }
}