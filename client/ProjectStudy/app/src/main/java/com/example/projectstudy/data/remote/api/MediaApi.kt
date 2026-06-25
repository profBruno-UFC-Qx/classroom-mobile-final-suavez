package com.example.projectstudy.data.remote.api

import android.content.Context
import android.net.Uri
import com.example.projectstudy.data.remote.dto.MediaUploadResponseDto
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MediaApi @Inject constructor(
    private val httpClient: HttpClient,
    @Named("BaseUrl") private val baseUrl: String,
    @ApplicationContext private val context: Context
) {
    suspend fun uploadActivityImage(
        token: String,
        imageUri: String
    ): MediaUploadResponseDto {
        val uri = Uri.parse(imageUri)

        val bytes = context.contentResolver
            .openInputStream(uri)
            .use { input ->
                requireNotNull(input) {
                    "Não foi possível abrir a imagem para upload."
                }

                input.readBytes()
            }

        val fileName = "activity_image_${System.currentTimeMillis()}.jpg"

        return httpClient.submitFormWithBinaryData(
            url = "$baseUrl/media/activity-image",
            formData = formData {
                append(
                    key = "file",
                    value = bytes,
                    headers = Headers.build {
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"$fileName\""
                        )
                        append(
                            HttpHeaders.ContentType,
                            "image/jpeg"
                        )
                    }
                )
            }
        ) {
            bearerAuth(token)
        }.body()
    }
}