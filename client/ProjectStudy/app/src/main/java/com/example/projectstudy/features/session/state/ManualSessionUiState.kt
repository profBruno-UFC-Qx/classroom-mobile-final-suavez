package com.example.projectstudy.features.session.state

import com.example.projectstudy.domain.model.Group

data class ManualSessionUiState(
    val isLoading: Boolean = false,
    val isPublishing: Boolean = false,

    val title: String = "",
    val subject: String = "",
    val description: String = "",

    val dateMillis: Long = System.currentTimeMillis(),

    val startHour: String = "",
    val startMinute: String = "",
    val endHour: String = "",
    val endMinute: String = "",

    val durationMinutes: Int = 0,

    val imageUrl: String = "",

    val availableGroups: List<Group> = emptyList(),
    val selectedGroupIds: List<String> = emptyList(),

    val titleError: String? = null,
    val subjectError: String? = null,
    val timeError: String? = null,
    val imageError: String? = null,
    val groupError: String? = null,

    val error: String? = null,
    val published: Boolean = false
)