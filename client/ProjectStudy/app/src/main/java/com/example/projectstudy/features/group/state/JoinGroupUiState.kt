package com.example.projectstudy.features.group.state

data class JoinGroupUiState(
    val inviteCode: String = "",
    val inviteCodeError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null,
    val joined: Boolean = false
)
