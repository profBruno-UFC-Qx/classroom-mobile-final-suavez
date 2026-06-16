package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.CreateManualSessionData

interface SessionRepository {
    suspend fun createManualSession(
        data: CreateManualSessionData
    )
}