package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.Group

interface GroupRepository {

    suspend fun getFirstUserGroup(): Group

    suspend fun getUserGroups(): List<Group>
}