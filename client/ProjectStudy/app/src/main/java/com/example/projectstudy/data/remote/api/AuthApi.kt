package com.example.projectstudy.data.remote.api

import com.example.projectstudy.data.remote.dto.LoginResponseDto
import com.example.projectstudy.data.remote.dto.RegisterRequestDto
import com.example.projectstudy.data.remote.dto.RegisterResponseDto
import com.example.projectstudy.data.remote.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import javax.inject.Inject
import javax.inject.Named

class AuthApi @Inject constructor(
    private val client: HttpClient,
    @Named("BaseUrl") private val baseUrl: String
) {
    suspend fun register(
        request: RegisterRequestDto
    ): RegisterResponseDto {
        return client.post("$baseUrl/auth/register") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json.toString()
            )
            setBody(request)
        }.body()
    }

    suspend fun login(
        username: String,
        password: String
    ): LoginResponseDto {
        return client.post("$baseUrl/auth/login") {
            setBody(
                FormDataContent(
                    Parameters.build {
                        append("grant_type", "password")
                        append("username", username)
                        append("password", password)
                    }
                )
            )
        }.body()
    }

    suspend fun getMe(token: String): UserDto {
        return client.get("$baseUrl/auth/me") {
            bearerAuth(token)
        }.body()
    }
}
