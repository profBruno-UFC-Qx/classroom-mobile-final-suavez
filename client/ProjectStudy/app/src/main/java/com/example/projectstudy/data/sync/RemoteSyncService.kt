package com.example.projectstudy.data.sync

import android.content.Context
import androidx.core.content.edit
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.remote.api.SyncApi
import com.example.projectstudy.data.remote.mapper.toEntity
import com.example.projectstudy.data.remote.mapper.toGroupRefs
import com.example.projectstudy.data.remote.mapper.toMediaEntities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSyncService @Inject constructor(
    @ApplicationContext context: Context,
    private val syncApi: SyncApi,
    private val groupDao: GroupDao,
    private val studyActivityDao: StudyActivityDao
) {
    private val preferences = context.getSharedPreferences(
        "sync_state",
        Context.MODE_PRIVATE
    )

    suspend fun pullAndSave(accessToken: String) {
        val lastSyncTimestamp = preferences.getLong(
            KEY_LAST_SYNC_TIMESTAMP,
            0L
        )

        val response = syncApi.pull(
            token = accessToken,
            lastSyncTimestamp = lastSyncTimestamp
        )

        val groupEntities = response.groups.map { group ->
            group.toEntity(
                serverTimestamp = response.serverTimestamp
            )
        }

        groupDao.upsertGroups(groupEntities)

        response.activities.forEach { activity ->
            studyActivityDao.upsertActivity(
                activity.toEntity()
            )

            studyActivityDao.deleteGroupRefsByActivityId(
                activityId = activity.id
            )

            studyActivityDao.deleteMediaByActivityId(
                activityId = activity.id
            )

            studyActivityDao.upsertActivityGroupRefs(
                activity.toGroupRefs()
            )

            studyActivityDao.upsertMedia(
                activity.toMediaEntities()
            )
        }

        preferences.edit {
            putLong(
                KEY_LAST_SYNC_TIMESTAMP,
                response.serverTimestamp
            )
        }
    }

    private companion object {
        const val KEY_LAST_SYNC_TIMESTAMP = "last_sync_timestamp"
    }
}
