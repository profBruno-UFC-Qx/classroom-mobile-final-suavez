package com.example.projectstudy.data.remote.api

import com.example.projectstudy.data.remote.dto.SyncActivityRequestDto
import com.example.projectstudy.data.remote.dto.SyncActivityResponseDto
import com.example.projectstudy.data.remote.dto.SyncPullResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import javax.inject.Inject
import javax.inject.Named

class SyncApi @Inject constructor(
    private val client: HttpClient,
    @Named("BaseUrl") private val baseUrl: String
) {
    suspend fun pull(
        token: String,
        lastSyncTimestamp: Long
    ): SyncPullResponseDto {
        return client.get("$baseUrl/sync/pull") {
            bearerAuth(token)
            parameter("lastSyncTimestamp", lastSyncTimestamp)
        }.body()
    }

    suspend fun pushActivities(
        token: String,
        activities: List<SyncActivityRequestDto>
    ): SyncActivityResponseDto {
        return client.post("$baseUrl/sync/activity") {
            bearerAuth(token)
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json.toString()
            )
            setBody(activities)
        }.body()
    }
}