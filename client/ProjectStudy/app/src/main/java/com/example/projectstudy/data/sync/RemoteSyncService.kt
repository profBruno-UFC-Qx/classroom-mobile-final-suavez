package com.example.projectstudy.data.sync

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.remote.api.SyncApi
import com.example.projectstudy.data.remote.mapper.toEntity
import com.example.projectstudy.data.remote.mapper.toGroupRefs
import com.example.projectstudy.data.remote.mapper.toMediaEntities
import com.example.projectstudy.data.remote.mapper.toSyncRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSyncService @Inject constructor(
    @ApplicationContext context: Context,
    private val syncApi: SyncApi,
    private val groupDao: GroupDao,
    private val rankingDao: RankingDao,
    private val studyActivityDao: StudyActivityDao
) {
    private val preferences = context.getSharedPreferences(
        "sync_state",
        Context.MODE_PRIVATE
    )

    suspend fun pullAndSave(accessToken: String) {
        pushPendingActivities(
            accessToken = accessToken
        )

        val lastSyncTimestamp = preferences.getLong(
            KEY_LAST_SYNC_TIMESTAMP,
            0L
        )

        val response = syncApi.pull(
            token = accessToken,
            lastSyncTimestamp = lastSyncTimestamp
        )

        Log.d(
            "RemoteSyncService",
            "Pull recebido: groups=${response.groups.size}, " +
                    "activities=${response.activities.size}, " +
                    "rankingEntries=${response.rankingEntries.size}, " +
                    "serverTimestamp=${response.serverTimestamp}"
        )

        val groupEntities = response.groups.map { group ->
            group.toEntity(
                serverTimestamp = response.serverTimestamp
            )
        }

        groupDao.upsertGroups(groupEntities)

        val knownGroupIds = groupDao.getGroups()
            .map { group ->
                group.id
            }
            .toSet()

        Log.d(
            "RemoteSyncService",
            "Grupos locais conhecidos: $knownGroupIds"
        )

        response.activities.forEach { activity ->
            val safeGroupRefs = activity.toGroupRefs()
                .filter { ref ->
                    ref.groupId in knownGroupIds
                }

            Log.d(
                "RemoteSyncService",
                "Preparando atividade ${activity.id}: title=${activity.title}, " +
                        "author=${activity.author.id}, groupIds=${activity.groupIds}, " +
                        "safeGroupRefs=${safeGroupRefs.map { it.groupId }}"
            )

            if (safeGroupRefs.isEmpty()) {
                Log.w(
                    "RemoteSyncService",
                    "Atividade ${activity.id} ignorada sem grupos locais válidos. " +
                            "groupIds=${activity.groupIds}"
                )

                return@forEach
            }

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
                safeGroupRefs
            )

            studyActivityDao.upsertMedia(
                activity.toMediaEntities()
            )

            Log.d(
                "RemoteSyncService",
                "Atividade salva localmente: ${activity.id} - ${activity.title}"
            )
        }

        Log.d(
            "RemoteSyncService",
            "Banco local após salvar atividades: " +
                    "activities=${studyActivityDao.countActivities()}, " +
                    "refs=${studyActivityDao.countActivityGroupRefs()}, " +
                    "group_1=${studyActivityDao.countActivitiesByGroupId("group_1")}, " +
                    "hex=${studyActivityDao.countActivitiesByUserId("usr_3ad96a53")}, " +
                    "ana=${studyActivityDao.countActivitiesByUserId("usr_teste_ana")}"
        )

        val rankingByGroupId = response.rankingEntries.groupBy { entry ->
            entry.groupId
        }

        for ((groupId, entries) in rankingByGroupId) {
            Log.d(
                "RemoteSyncService",
                "Salvando ranking do grupo $groupId com ${entries.size} entradas"
            )

            rankingDao.deleteRankingByGroupId(
                groupId = groupId
            )

            rankingDao.upsertRanking(
                entries.map { entry ->
                    entry.toEntity()
                }
            )

            val savedEntries = rankingDao.getRankingByGroupIdOnce(
                groupId = groupId
            )

            Log.d(
                "RemoteSyncService",
                "Ranking local salvo para $groupId: ${savedEntries.size} entradas"
            )
        }

        preferences.edit {
            putLong(
                KEY_LAST_SYNC_TIMESTAMP,
                response.serverTimestamp
            )
        }
    }

    private suspend fun pushPendingActivities(
        accessToken: String
    ) {
        val pendingActivities = studyActivityDao.getPendingSyncActivities()

        if (pendingActivities.isEmpty()) {
            return
        }

        val requests = pendingActivities.map { activity ->
            val groupIds = studyActivityDao.getGroupIdsByActivityId(
                activityId = activity.id
            )

            val mediaUris = studyActivityDao.getMediaUrisByActivityId(
                activityId = activity.id
            )

            activity.toSyncRequest(
                groupIds = groupIds,
                mediaUris = mediaUris
            )
        }

        val response = syncApi.pushActivities(
            token = accessToken,
            activities = requests
        )

        val syncedIds = response.syncedIds.toSet()

        pendingActivities
            .filter { activity -> activity.id in syncedIds }
            .forEach { activity ->
                studyActivityDao.upsertActivity(
                    activity.copy(
                        isSynced = true,
                        pendingSyncAction = null
                    )
                )
            }
    }

    private companion object {
        const val KEY_LAST_SYNC_TIMESTAMP = "last_sync_timestamp"
    }
}