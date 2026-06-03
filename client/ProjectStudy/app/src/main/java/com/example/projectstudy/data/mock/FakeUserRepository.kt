package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.User
import com.example.projectstudy.data.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeUserRepository @Inject constructor() : UserRepository {
    override suspend fun getCurrentUser(): User {
        delay(500)

        return MockData.getCurrentUser()
    }
}