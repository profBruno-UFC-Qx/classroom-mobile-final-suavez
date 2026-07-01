package com.example.projectstudy.features.auth.state

sealed interface RegisterEvent {

    data class NameChanged(
        val value: String
    ) : RegisterEvent

    data class UsernameChanged(
        val value: String
    ) : RegisterEvent

    data class EmailChanged(
        val value: String
    ) : RegisterEvent

    data class InstitutionChanged(
        val value: String
    ) : RegisterEvent

    data class CourseChanged(
        val value: String
    ) : RegisterEvent

    data class PasswordChanged(
        val value: String
    ) : RegisterEvent

    data class ConfirmPasswordChanged(
        val value: String
    ) : RegisterEvent

    data object RegisterClicked : RegisterEvent

    data object RegisterHandled : RegisterEvent
}