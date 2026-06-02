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

    data class StartTimeChanged(
        val minutes: Int
    ) : ManualSessionEvent

    data class DurationChanged(
        val value: String
    ) : ManualSessionEvent

    data class MediaSelected(
        val uris: List<String>
    ) : ManualSessionEvent

    data class MediaRemoved(
        val uri: String
    ) : ManualSessionEvent

    data class GroupToggled(
        val groupId: String
    ) : ManualSessionEvent

    data object PublishClicked : ManualSessionEvent

    data object PublishedHandled : ManualSessionEvent
}