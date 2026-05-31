package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.repository.GroupRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeGroupRepository @Inject constructor() : GroupRepository {

    override suspend fun getFirstUserGroup(): Group {
        delay(500)

        return MockData.groups.first()
    }
}