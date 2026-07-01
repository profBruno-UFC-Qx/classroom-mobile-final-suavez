package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.GroupRepository
import javax.inject.Inject

/**
 * Caso de uso responsável por entrar em um grupo existente usando um código
 * de convite.
 */
class JoinGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        token: String,
        inviteCode: String
    ): Result<Unit> {
        return groupRepository.joinGroup(
            token = token,
            inviteCode = inviteCode
        )
    }
}
