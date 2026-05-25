package com.example.projectstudy.domain.repository

import com.example.projectstudy.domain.model.Activity

interface ActivityRepository {
    suspend fun getFeed(): List<Activity>
}