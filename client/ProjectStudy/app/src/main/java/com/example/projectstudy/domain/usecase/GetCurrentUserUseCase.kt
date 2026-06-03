package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke() =
        repository.getCurrentUser()
}