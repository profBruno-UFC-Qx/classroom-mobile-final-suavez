package com.example.projectstudy.di

import android.util.Log
import com.example.projectstudy.data.remote.api.AuthApi
import com.example.projectstudy.data.remote.dto.ErrorResponseDto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.client.request.accept
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @Named("BaseUrl")
    fun provideBaseUrl(): String {
        return "https://projectstudyserver.vercel.app"
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        explicitNulls = false
                        encodeDefaults = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor", message)
                    }
                }

                level = LogLevel.BODY
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    val responseException = exception as? ResponseException
                        ?: return@handleResponseExceptionWithRequest

                    val detail = runCatching {
                        responseException.response
                            .body<ErrorResponseDto>()
                            .detail
                    }.getOrNull()

                    if (!detail.isNullOrBlank()) {
                        throw Exception(detail, exception)
                    }
                }
            }

            defaultRequest {
                accept(ContentType.Application.Json)
            }
        }
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        client: HttpClient,
        @Named("BaseUrl") baseUrl: String
    ): AuthApi {
        return AuthApi(
            client = client,
            baseUrl = baseUrl
        )
    }
}
