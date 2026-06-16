package com.example.projectstudy.features.auth.state

sealed interface LoginEvent {

    data class EmailOrUsernameChanged(
        val value: String
    ) : LoginEvent

    data class PasswordChanged(
        val value: String
    ) : LoginEvent

    data object LoginClicked : LoginEvent
    data object LoginHandled : LoginEvent

}