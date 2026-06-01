package com.example.projectstudy.domain.repository

import com.example.projectstudy.domain.model.StudyActivity

interface ActivityRepository {

    suspend fun getActivitiesByGroupId(groupId: String): List<StudyActivity>

    suspend fun getActivitiesByUserId(userId: String): List<StudyActivity>

}