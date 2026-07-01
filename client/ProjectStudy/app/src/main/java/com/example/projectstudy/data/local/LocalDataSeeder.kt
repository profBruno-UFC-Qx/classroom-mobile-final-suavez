package com.example.projectstudy.data.local

import androidx.room.withTransaction
import com.example.projectstudy.data.mapper.toEntity
import com.example.projectstudy.data.mapper.toGroupRefs
import com.example.projectstudy.data.mapper.toMediaEntities
import com.example.projectstudy.data.mock.MockData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSeeder @Inject constructor(
    private val database: ProjectStudyDatabase
) {

    private val seedMutex = Mutex()

    suspend fun seedIfNeeded() {
        seedMutex.withLock {
            val firstGroup = database.groupDao().getFirstGroup()

            if (firstGroup != null) {
                return
            }

            database.withTransaction {
                seedUsers()
                seedGroups()
                seedActivities()
                seedRanking()
            }
        }
    }

    private suspend fun seedUsers() {
        val users = MockData.getUsers().map { user ->
            user.toEntity()
        }

        database.userDao().upsertUsers(users)
    }

    private suspend fun seedGroups() {
        val groups = MockData.getUserGroups().map { group ->
            group.toEntity()
        }

        database.groupDao().upsertGroups(groups)
    }

    private suspend fun seedActivities() {
        val activities = MockData.getActivities()

        database.studyActivityDao().upsertActivities(
            activities.map { activity ->
                activity.toEntity()
            }
        )

        activities.forEach { activity ->
            database.studyActivityDao().deleteGroupRefsByActivityId(activity.id)
            database.studyActivityDao().deleteMediaByActivityId(activity.id)

            database.studyActivityDao().upsertActivityGroupRefs(
                activity.toGroupRefs()
            )

            database.studyActivityDao().upsertMedia(
                activity.toMediaEntities()
            )
        }
    }

    private suspend fun seedRanking() {
        MockData.getUserGroups().forEach { group ->
            val ranking = MockData.getRankingByGroupId(group.id)

            database.rankingDao().deleteRankingByGroupId(group.id)

            database.rankingDao().upsertRanking(
                ranking.map { entry ->
                    entry.toEntity()
                }
            )
        }
    }
}