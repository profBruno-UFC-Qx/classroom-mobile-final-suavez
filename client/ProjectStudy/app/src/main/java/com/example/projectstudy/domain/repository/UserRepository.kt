package com.example.projectstudy.domain.repository

import com.example.projectstudy.domain.model.User

interface UserRepository {
    suspend fun getCurrentUser(): User
}