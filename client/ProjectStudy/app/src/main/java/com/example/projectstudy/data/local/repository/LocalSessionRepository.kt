package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.local.dao.UserDao
import com.example.projectstudy.data.local.entity.RankingEntryEntity
import com.example.projectstudy.data.mapper.toEntity
import com.example.projectstudy.data.mapper.toGroupRefs
import com.example.projectstudy.data.mapper.toMediaEntities
import com.example.projectstudy.data.repository.AuthRepository
import com.example.projectstudy.data.repository.SessionRepository
import com.example.projectstudy.domain.model.ActivityAuthor
import com.example.projectstudy.domain.model.CreateManualSessionData
import com.example.projectstudy.domain.model.StudyActivity
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.UUID
import javax.inject.Inject
import com.example.projectstudy.data.sync.RemoteSyncService

class LocalSessionRepository @Inject constructor(
    private val studyActivityDao: StudyActivityDao,
    private val rankingDao: RankingDao,
    private val userDao: UserDao,
    private val groupDao: GroupDao,
    private val authRepository: AuthRepository,
    private val remoteSyncService: RemoteSyncService
) : SessionRepository {

    override suspend fun createManualSession(
        data: CreateManualSessionData
    ) {
        val currentUserId = authRepository.getCurrentUserId()

        require(!currentUserId.isNullOrBlank()) {
            "Nenhum usuário autenticado encontrado na sessão local."
        }

        val currentUser = userDao.getUserById(
            userId = currentUserId
        )

        requireNotNull(currentUser) {
            "Usuário autenticado não encontrado no banco local."
        }

        val firstMediaUri = data.mediaUris.firstOrNull().orEmpty()

        val startedAtMillis = buildStartedAtMillis(
            dateMillis = data.dateMillis,
            startTimeMinutes = data.startTimeMinutes
        )

        val endedAtMillis = startedAtMillis + data.durationMinutes * 60_000L

        val activity = StudyActivity(
            id = UUID.randomUUID().toString(),
            groupIds = data.groupIds,
            author = ActivityAuthor(
                id = currentUser.id,
                name = currentUser.name,
                avatarInitials = currentUser.avatarInitials,
                avatarUrl = currentUser.avatarUrl
            ),
            title = data.title,
            subject = data.subject,
            description = data.description,
            durationMinutes = data.durationMinutes,
            imageUrl = firstMediaUri,
            mediaUris = data.mediaUris,
            reactions = 0,
            startedAtMillis = startedAtMillis,
            endedAtMillis = endedAtMillis,
            createdAtMillis = System.currentTimeMillis(),
            isManual = true
        )

        studyActivityDao.upsertActivity(
            activity.toEntity(
                isSynced = false,
                pendingSyncAction = "CREATE"
            )
        )

        studyActivityDao.deleteGroupRefsByActivityId(activity.id)
        studyActivityDao.deleteMediaByActivityId(activity.id)

        studyActivityDao.upsertActivityGroupRefs(
            activity.toGroupRefs()
        )

        studyActivityDao.upsertMedia(
            activity.toMediaEntities()
        )

        updateRankingAfterSession(
            groupIds = data.groupIds,
            userId = currentUser.id,
            durationMinutes = data.durationMinutes
        )

        updateGroupProgressAfterSession(
            groupIds = data.groupIds,
            durationMinutes = data.durationMinutes
        )

        authRepository.getAccessToken()?.let { token ->
            runCatching {
                remoteSyncService.pullAndSave(
                    accessToken = token
                )
            }
        }
    }

    private suspend fun updateRankingAfterSession(
        groupIds: List<String>,
        userId: String,
        durationMinutes: Int
    ) {
        val currentUser = userDao.getUserById(
            userId = userId
        )

        requireNotNull(currentUser) {
            "Usuário autenticado não encontrado no banco local."
        }

        groupIds.forEach { groupId ->
            val currentRanking = rankingDao.getRankingByGroupIdOnce(
                groupId = groupId
            )

            val userAlreadyInRanking = currentRanking.any { entry ->
                entry.userId == userId
            }

            val rankingWithUpdatedUser = currentRanking.map { entry ->
                if (entry.userId == userId) {
                    entry.copy(
                        totalMinutes = entry.totalMinutes + durationMinutes,
                        activeDays = entry.activeDays + 1,
                        lastSyncedAtMillis = System.currentTimeMillis()
                    )
                } else {
                    entry
                }
            }.toMutableList()

            if (!userAlreadyInRanking) {
                rankingWithUpdatedUser.add(
                    RankingEntryEntity(
                        id = "${groupId}_$userId",
                        groupId = groupId,
                        userId = userId,
                        userDisplayName = currentUser.name,
                        username = currentUser.username,
                        userEmail = currentUser.email,
                        userInstitution = currentUser.institution,
                        userCourse = currentUser.course,
                        userAvatarInitials = currentUser.avatarInitials,
                        userAvatarUrl = currentUser.avatarUrl,
                        totalMinutes = durationMinutes,
                        activeDays = 1,
                        position = rankingWithUpdatedUser.size + 1,
                        isCurrentUser = true,
                        lastSyncedAtMillis = System.currentTimeMillis()
                    )
                )
            }

            val updatedRanking = rankingWithUpdatedUser
                .sortedWith(
                    compareByDescending<RankingEntryEntity> { entry ->
                        entry.totalMinutes
                    }.thenByDescending { entry ->
                        entry.activeDays
                    }
                )
                .mapIndexed { index, entry ->
                    entry.copy(
                        position = index + 1
                    )
                }

            rankingDao.upsertRanking(updatedRanking)
        }
    }

    private fun buildStartedAtMillis(
        dateMillis: Long,
        startTimeMinutes: Int
    ): Long {
        val localZone = ZoneId.systemDefault()

        val selectedDate = Instant
            .ofEpochMilli(dateMillis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()

        val selectedTime = LocalTime.of(
            startTimeMinutes / 60,
            startTimeMinutes % 60
        )

        return selectedDate
            .atTime(selectedTime)
            .atZone(localZone)
            .toInstant()
            .toEpochMilli()
    }

    private suspend fun updateGroupProgressAfterSession(
        groupIds: List<String>,
        durationMinutes: Int
    ) {
        groupIds.forEach { groupId ->
            val group = groupDao.getGroupByIdOnce(
                groupId = groupId
            ) ?: return@forEach

            val updatedGroup = group.copy(
                currentMinutes = group.currentMinutes + durationMinutes,
                userMinutes = group.userMinutes + durationMinutes,
                lastSyncedAtMillis = System.currentTimeMillis()
            )

            groupDao.upsertGroup(updatedGroup)
        }
    }

}