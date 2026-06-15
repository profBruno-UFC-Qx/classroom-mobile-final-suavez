package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.GroupRepository
import com.example.projectstudy.domain.model.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalGroupRepository @Inject constructor(
    private val groupDao: GroupDao,
    private val localDataSeeder: LocalDataSeeder
) : GroupRepository {

    override fun observeFirstUserGroup(): Flow<Group> {
        return flow {
            localDataSeeder.seedIfNeeded()

            emitAll(
                groupDao.observeGroups()
                    .map { groups ->
                        requireNotNull(groups.firstOrNull()) {
                            "Nenhum grupo encontrado no banco local"
                        }.toDomain()
                    }
            )


            val group = groupDao.getFirstGroup()

            requireNotNull(group) {
                "Nenhum grupo encontrado no banco local."
            }.toDomain()
        }

    }

    override fun observeUserGroups(): Flow<List<Group>> {
        return flow {
            localDataSeeder.seedIfNeeded()

            emitAll(
                groupDao.observeGroups()
                    .map { groups ->
                        groups.map { group ->
                            group.toDomain()
                        }
                    }
            )

        }
    }
}