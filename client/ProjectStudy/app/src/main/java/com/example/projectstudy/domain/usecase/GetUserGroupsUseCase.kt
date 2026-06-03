package com.example.projectstudy.domain.usecase

import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.data.repository.GroupRepository
import javax.inject.Inject

class GetUserGroupsUseCase @Inject constructor(
    private val repository: GroupRepository
) {
    suspend operator fun invoke(): List<Group> {
        return repository.getUserGroups()
    }
}