package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.ActivityRepository
import com.example.projectstudy.domain.model.StudyActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalActivityRepository @Inject constructor(
    private val studyActivityDao: StudyActivityDao,
    private val localDataSeeder: LocalDataSeeder
) : ActivityRepository {

    override fun observeActivitiesByGroupId(
        groupId: String
    ): Flow<List<StudyActivity>> {
        return flow {
            localDataSeeder.seedIfNeeded()

            studyActivityDao.observeActivitiesByGroupId(groupId)
                .map { activities ->
                    activities.map { activity ->
                        activity.toDomain()
                    }
                }
                .collect { activities ->
                    emit(activities)
                }
        }
    }

    override fun observeActivitiesByUserId(
        userId: String
    ): Flow<List<StudyActivity>> {
        return flow {
            localDataSeeder.seedIfNeeded()

            studyActivityDao.observeActivitiesByUserId(userId)
                .map { activities ->
                    activities.map { activity ->
                        activity.toDomain()
                    }
                }
                .collect { activities ->
                    emit(activities)
                }
        }
    }
}