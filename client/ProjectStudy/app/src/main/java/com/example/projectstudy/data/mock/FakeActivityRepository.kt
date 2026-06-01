package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.repository.ActivityRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeActivityRepository @Inject constructor() : ActivityRepository {

    override suspend fun getActivitiesByGroupId(
        groupId: String
    ): List<StudyActivity> {
        delay(1000)

        return MockData.getActivitiesByGroupId(groupId)
    }

    override suspend fun getActivitiesByUserId(
        userId: String
    ): List<StudyActivity> {
        delay(500)

        return MockData.getActivitiesByUserId(userId)
    }


}