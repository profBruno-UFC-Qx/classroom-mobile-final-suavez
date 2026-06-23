package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.UserDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.AuthRepository
import com.example.projectstudy.data.repository.UserRepository
import com.example.projectstudy.domain.model.User
import javax.inject.Inject

/**
 * Implementação local do repositório de usuário.
 *
 * Em uma arquitetura offline-first, o usuário atual é lido do banco local.
 * O ID do usuário autenticado vem da sessão salva localmente após login/cadastro.
 */
class LocalUserRepository @Inject constructor(
    private val userDao: UserDao,
    private val localDataSeeder: LocalDataSeeder,
    private val authRepository: AuthRepository
) : UserRepository {

    override suspend fun getCurrentUser(): User {
        localDataSeeder.seedIfNeeded()

        val currentUserId = authRepository.getCurrentUserId()

        require(!currentUserId.isNullOrBlank()) {
            "Nenhum usuário autenticado encontrado na sessão local."
        }

        val user = userDao.getUserById(
            userId = currentUserId
        )

        return requireNotNull(user) {
            "Usuário autenticado não encontrado no banco local."
        }.toDomain()
    }
}
