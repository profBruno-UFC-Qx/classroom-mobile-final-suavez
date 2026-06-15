package com.example.projectstudy.data.mock

import com.example.projectstudy.data.repository.ActivityRepository
import com.example.projectstudy.domain.model.StudyActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeActivityRepository @Inject constructor() : ActivityRepository {

    override fun observeActivitiesByGroupId(
        groupId: String
    ): Flow<List<StudyActivity>> {
        return flow {
            delay(500)

            emit(
                MockData.getActivitiesByGroupId(groupId)
            )
        }
    }

    override fun observeActivitiesByUserId(
        userId: String
    ): Flow<List<StudyActivity>> {
        return flow {
            delay(500)

            emit(
                MockData.getActivitiesByUserId(userId)
            )
        }
    }
}