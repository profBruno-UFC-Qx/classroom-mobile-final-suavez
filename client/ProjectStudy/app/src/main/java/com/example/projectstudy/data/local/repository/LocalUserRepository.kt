package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.UserDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.UserRepository
import com.example.projectstudy.domain.model.User
import javax.inject.Inject

/**
 * Implementação local do repositório de usuário.
 *
 * Essa classe fornece os dados do usuário atual a partir do banco local.
 * Antes de buscar o usuário, ela garante que o seed inicial foi executado,
 * permitindo que o app tenha um usuário disponível mesmo sem integração com API.
 *
 * Atualmente, o app ainda não possui autenticação real com backend. Por isso,
 * o usuário atual é identificado por um ID local fixo. Futuramente, esse ID
 * deve vir da sessão autenticada, token ou outro mecanismo de autenticação.
 */
class LocalUserRepository @Inject constructor(
    private val userDao: UserDao,
    private val localDataSeeder: LocalDataSeeder
) : UserRepository {

    /**
     * Busca o usuário atualmente autenticado no banco local.
     *
     * O método executa o seed inicial antes da consulta para garantir que o
     * usuário padrão exista no banco. Em seguida, converte a entidade local
     * para o modelo de domínio usado pelas telas e demais camadas do app.
     *
     * @return Usuário atual convertido para o modelo de domínio.
     *
     * @throws IllegalArgumentException se o usuário atual não for encontrado
     * no banco local.
     */
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