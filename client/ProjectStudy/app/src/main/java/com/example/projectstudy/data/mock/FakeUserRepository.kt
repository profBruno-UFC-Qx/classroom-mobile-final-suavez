package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.User
import com.example.projectstudy.data.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class FakeUserRepository @Inject constructor() : UserRepository {
    override suspend fun getCurrentUser(): User {
        delay(500.milliseconds)

        return MockData.getCurrentUser()
    }
}