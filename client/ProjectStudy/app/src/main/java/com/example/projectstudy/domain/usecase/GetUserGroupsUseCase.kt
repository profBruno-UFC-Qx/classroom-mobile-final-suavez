package com.example.projectstudy.domain.usecase

import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.data.repository.GroupRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetUserGroupsUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(): Flow<List<Group>> {
        return groupRepository.observeUserGroups()
    }
}