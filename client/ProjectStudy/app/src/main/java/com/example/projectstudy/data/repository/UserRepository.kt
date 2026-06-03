package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.User

interface UserRepository {
    suspend fun getCurrentUser(): User
}