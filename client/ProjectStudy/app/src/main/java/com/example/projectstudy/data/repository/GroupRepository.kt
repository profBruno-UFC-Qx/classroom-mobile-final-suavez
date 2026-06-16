package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun observeFirstUserGroup(): Flow<Group>

    fun observeUserGroups(): Flow<List<Group>>

//    suspend fun getFirstUserGroup(): Group
//
//    suspend fun getUserGroups(): List<Group>
}