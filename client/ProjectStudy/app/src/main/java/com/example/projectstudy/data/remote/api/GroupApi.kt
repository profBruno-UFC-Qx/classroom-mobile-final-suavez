package com.example.projectstudy.data.remote.api

import com.example.projectstudy.data.remote.dto.GroupRemoteDto
import com.example.projectstudy.data.remote.dto.JoinGroupRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import javax.inject.Inject
import javax.inject.Named

class GroupApi @Inject constructor(
    private val client: HttpClient,
    @Named("BaseUrl") private val baseUrl: String
) {
    suspend fun join(
        token: String,
        inviteCode: String
    ): GroupRemoteDto {
        return client.post("$baseUrl/group/join") {
            bearerAuth(token)
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json.toString()
            )
            setBody(JoinGroupRequestDto(inviteCode = inviteCode))
        }.body()
    }
}
