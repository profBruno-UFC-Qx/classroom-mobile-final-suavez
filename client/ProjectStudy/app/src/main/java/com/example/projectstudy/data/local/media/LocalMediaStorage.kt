package com.example.projectstudy.data.local.media

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalMediaStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun saveImageLocally(sourceUri: Uri): String {
        val imagesDir = File(
            context.filesDir,
            "activity_images"
        )

        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }

        val fileName = "${UUID.randomUUID()}.jpg"

        val destinationFile = File(
            imagesDir,
            fileName
        )

        context.contentResolver.openInputStream(sourceUri).use { input ->
            requireNotNull(input) {
                "Não foi possível abrir a imagem selecionada."
            }

            destinationFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return Uri.fromFile(destinationFile).toString()
    }
}