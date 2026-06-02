package com.example.projectstudy.features.session.state

import com.example.projectstudy.domain.model.Group
import java.util.Calendar

data class ManualSessionUiState(
    val isLoading: Boolean = false,
    val isPublishing: Boolean = false,

    val title: String = "",
    val subject: String = "",
    val description: String = "",

    val dateMillis: Long = System.currentTimeMillis(),

    val startTimeMinutes: Int = currentTimeInMinutes(),
    val durationText: String = "60",
    val durationMinutes: Int = 60,

    val selectedMediaUris: List<String> = emptyList(),

    val availableGroups: List<Group> = emptyList(),
    val selectedGroupIds: List<String> = emptyList(),

    val titleError: String? = null,
    val subjectError: String? = null,
    val durationError: String? = null,
    val mediaError: String? = null,
    val groupError: String? = null,

    val error: String? = null,
    val published: Boolean = false
)

private fun currentTimeInMinutes(): Int {
    val calendar = Calendar.getInstance()

    return calendar.get(Calendar.HOUR_OF_DAY) * 60 +
            calendar.get(Calendar.MINUTE)
}