package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.StudyActivity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {

    fun observeActivitiesByGroupId(
        groupId: String
    ): Flow<List<StudyActivity>>

    fun observeActivitiesByUserId(
        userId: String
    ): Flow<List<StudyActivity>>
}