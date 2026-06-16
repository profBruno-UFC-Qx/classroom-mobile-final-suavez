package com.example.projectstudy.data.repository

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val isLoggedIn: StateFlow<Boolean>

    suspend fun setLoggedIn(value: Boolean)

    suspend fun logout()
}