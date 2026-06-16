package com.example.projectstudy.data.mock

import com.example.projectstudy.data.repository.SessionRepository
import com.example.projectstudy.domain.model.CreateManualSessionData
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeSessionRepository @Inject constructor() : SessionRepository {
    override suspend fun createManualSession(
        data: CreateManualSessionData
    ) {
        delay(500)
    }
}