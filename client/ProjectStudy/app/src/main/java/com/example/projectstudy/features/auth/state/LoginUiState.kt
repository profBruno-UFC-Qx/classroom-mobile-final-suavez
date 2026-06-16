package com.example.projectstudy.features.auth.state

data class LoginUiState (
    val emailOrUsername: String = "",
    val password: String = "",

    val emailOrUsernameError: String? = null,
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null,
    val loggedIn: Boolean = false
)