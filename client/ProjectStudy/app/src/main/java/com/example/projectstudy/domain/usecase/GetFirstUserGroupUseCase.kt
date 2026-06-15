package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.GroupRepository
import com.example.projectstudy.domain.model.Group
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFirstUserGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {

    operator fun invoke(): Flow<Group> {
        return groupRepository.observeFirstUserGroup()
    }
}