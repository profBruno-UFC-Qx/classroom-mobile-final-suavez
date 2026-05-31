package com.example.projectstudy.domain.usecase

import com.example.projectstudy.domain.repository.GroupRepository
import javax.inject.Inject

class GetFirstUserGroupUseCase @Inject constructor(
    private val repository: GroupRepository
) {

    suspend operator fun invoke() =
        repository.getFirstUserGroup()
}