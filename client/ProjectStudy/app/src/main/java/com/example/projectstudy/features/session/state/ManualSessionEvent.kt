package com.example.projectstudy.features.session.state

sealed interface ManualSessionEvent {

    data class TitleChanged(
        val value: String
    ) : ManualSessionEvent

    data class SubjectChanged(
        val value: String
    ) : ManualSessionEvent

    data class DescriptionChanged(
        val value: String
    ) : ManualSessionEvent

    data class DateChanged(
        val millis: Long
    ) : ManualSessionEvent

    data class StartHourChanged(
        val value: String
    ) : ManualSessionEvent

    data class StartMinuteChanged(
        val value: String
    ) : ManualSessionEvent

    data class EndHourChanged(
        val value: String
    ) : ManualSessionEvent

    data class EndMinuteChanged(
        val value: String
    ) : ManualSessionEvent

    data class ImageUrlChanged(
        val value: String
    ) : ManualSessionEvent

    data class GroupToggled(
        val groupId: String
    ) : ManualSessionEvent

    data object PublishClicked : ManualSessionEvent

    data object PublishedHandled : ManualSessionEvent
}