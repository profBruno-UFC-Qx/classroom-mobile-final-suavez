package com.example.projectstudy.features.group.state

sealed interface JoinGroupEvent {

    data class InviteCodeChanged(
        val value: String
    ) : JoinGroupEvent

    data object JoinClicked : JoinGroupEvent
    data object JoinHandled : JoinGroupEvent

}
