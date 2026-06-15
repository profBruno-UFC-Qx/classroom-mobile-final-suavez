package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.ActivityRepository
import com.example.projectstudy.domain.model.StudyActivity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupActivitiesUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {

    operator fun invoke(
        groupId: String
    ): Flow<List<StudyActivity>> {
        return activityRepository.observeActivitiesByGroupId(groupId)
    }
}