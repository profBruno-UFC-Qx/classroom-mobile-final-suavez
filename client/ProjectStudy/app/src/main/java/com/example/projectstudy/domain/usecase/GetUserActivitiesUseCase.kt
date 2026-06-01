package com.example.projectstudy.domain.usecase

import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.repository.ActivityRepository
import javax.inject.Inject

class GetUserActivitiesUseCase @Inject constructor(
    private val repository: ActivityRepository
) {

    suspend operator fun invoke(
        userId: String
    ): List<StudyActivity> {
        return repository.getActivitiesByUserId(userId)
    }
}