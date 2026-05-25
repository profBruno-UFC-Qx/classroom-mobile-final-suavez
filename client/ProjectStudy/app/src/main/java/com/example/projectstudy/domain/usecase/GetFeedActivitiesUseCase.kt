package com.example.projectstudy.domain.usecase

import com.example.projectstudy.domain.repository.ActivityRepository
import javax.inject.Inject

class GetFeedActivitiesUseCase @Inject constructor(
    private val repository: ActivityRepository
) {

    suspend operator fun invoke() =
        repository.getFeedActivities()

}