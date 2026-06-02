package com.example.projectstudy.data.mock

import com.example.projectstudy.core.util.buildEndedAtMillis
import com.example.projectstudy.core.util.buildStartedAtMillis
import com.example.projectstudy.domain.model.ActivityAuthor
import com.example.projectstudy.domain.model.CreateManualSessionData
import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.repository.SessionRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeSessionRepository @Inject constructor() : SessionRepository {

    override suspend fun createManualSession(
        data: CreateManualSessionData
    ): StudyActivity {
        delay(800)

        val currentUser = MockData.getCurrentUser()

        val author = ActivityAuthor(
            id = currentUser.id,
            name = currentUser.name,
            avatarInitials = currentUser.avatarInitials,
            avatarUrl = currentUser.avatarUrl
        )

        val startedAtMillis = buildStartedAtMillis(
            dateMillis = data.dateMillis,
            startTimeMinutes = data.startTimeMinutes
        )

        val endedAtMillis = buildEndedAtMillis(
            startedAtMillis = startedAtMillis,
            durationMinutes = data.durationMinutes
        )

        return StudyActivity(
            id = "manual_${System.currentTimeMillis()}",
            groupIds = data.groupIds,
            author = author,
            title = data.title,
            subject = data.subject,
            description = data.description,
            durationMinutes = data.durationMinutes,
            imageUrl = data.mediaUris.firstOrNull().orEmpty(),
            mediaUris = data.mediaUris,
            reactions = 0,
            startedAtMillis = startedAtMillis,
            endedAtMillis = endedAtMillis,
            createdAtMillis = System.currentTimeMillis(),
            isManual = true
        )
    }
}