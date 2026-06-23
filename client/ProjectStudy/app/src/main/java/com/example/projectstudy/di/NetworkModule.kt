package com.example.projectstudy.di

import android.util.Log
import com.example.projectstudy.data.remote.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
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
        return "http://10.0.2.2:8000"
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
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
