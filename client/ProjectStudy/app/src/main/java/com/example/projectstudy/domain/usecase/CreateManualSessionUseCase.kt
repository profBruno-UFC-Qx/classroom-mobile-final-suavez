package com.example.projectstudy.domain.usecase

import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.model.CreateManualSessionData
import com.example.projectstudy.data.repository.SessionRepository
import javax.inject.Inject

class CreateManualSessionUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(
        data: CreateManualSessionData
    ) {
        return repository.createManualSession(data)
    }
}