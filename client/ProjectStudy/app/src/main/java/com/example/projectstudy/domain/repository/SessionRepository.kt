package com.example.projectstudy.domain.repository


import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.model.CreateManualSessionData

interface SessionRepository {
    suspend fun createManualSession(data: CreateManualSessionData): StudyActivity
}