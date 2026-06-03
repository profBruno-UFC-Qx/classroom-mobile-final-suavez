package com.example.projectstudy.data.repository


import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.model.CreateManualSessionData

interface SessionRepository {
    suspend fun createManualSession(data: CreateManualSessionData): StudyActivity
}