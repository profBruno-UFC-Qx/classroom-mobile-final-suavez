package com.example.projectstudy.features.feed.state

sealed interface FeedEvent {

    data object Refresh : FeedEvent

}