package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.data.repository.GroupRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeGroupRepository @Inject constructor() : GroupRepository {

    override fun observeFirstUserGroup(): Flow<Group> {
        return flow {
            delay(500)

            emit(
                MockData.getUserGroups().first()
            )
        }
    }

    override fun observeUserGroups(): Flow<List<Group>> {
        return flow {
            delay(500)

            emit(
                MockData.getUserGroups()
            )
        }
    }
}