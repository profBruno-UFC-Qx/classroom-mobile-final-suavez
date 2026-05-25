package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.Activity
import com.example.projectstudy.domain.repository.ActivityRepository
import kotlinx.coroutines.delay

class FakeActivityRepository : ActivityRepository {
    override suspend fun getFeed(): List<Activity> {
        delay(800) // para test :)
        return MockData.mockFeed
    }
}