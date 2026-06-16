package com.example.projectstudy.features.auth.state

data class RegisterUiState(
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val institution: String = "",
    val course: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val nameError: String? = null,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null,
    val registered: Boolean = false
)