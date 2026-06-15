package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.UserDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.UserRepository
import com.example.projectstudy.domain.model.User
import javax.inject.Inject

class LocalUserRepository @Inject constructor(
    private val userDao: UserDao,
    private val localDataSeeder: LocalDataSeeder
) : UserRepository {

    override suspend fun getCurrentUser(): User {
        localDataSeeder.seedIfNeeded()

        val currentUserId = "user_4"

        val user = userDao.getUserById(
            userId = currentUserId
        )

        return requireNotNull(user) {
            "Usuário atual não encontrado no banco local."
        }.toDomain()
    }
}