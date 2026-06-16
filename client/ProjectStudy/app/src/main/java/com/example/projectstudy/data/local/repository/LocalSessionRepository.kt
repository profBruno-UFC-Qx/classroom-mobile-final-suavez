package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.local.dao.UserDao
import com.example.projectstudy.data.local.entity.RankingEntryEntity
import com.example.projectstudy.data.mapper.toEntity
import com.example.projectstudy.data.mapper.toGroupRefs
import com.example.projectstudy.data.mapper.toMediaEntities
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

/**
 * Implementação local do repositório de sessões de estudo.
 *
 * Essa classe é responsável por salvar sessões manuais diretamente no banco local.
 * Como o app segue uma abordagem offline-first, a sessão é registrada primeiro
 * no Room e marcada como pendente de sincronização.
 *
 * Ao criar uma sessão manual, este repositório também atualiza os dados derivados:
 * - salva a atividade de estudo;
 * - associa a atividade aos grupos selecionados;
 * - salva as mídias anexadas;
 * - atualiza o ranking dos grupos;
 * - atualiza o progresso dos grupos.
 */
class LocalSessionRepository @Inject constructor(
    private val studyActivityDao: StudyActivityDao,
    private val rankingDao: RankingDao,
    private val userDao: UserDao,
    private val groupDao: GroupDao
) : SessionRepository {

    /**
     * Cria uma sessão manual de estudo no banco local.
     *
     * A sessão é salva como uma [StudyActivity] e marcada com:
     * - `isSynced = false`, indicando que ainda não foi sincronizada com API;
     * - `pendingSyncAction = "CREATE"`, indicando que a ação futura será criação remota.
     *
     * Após salvar a atividade, o método atualiza os vínculos com grupos, mídias,
     * ranking e progresso dos grupos afetados.
     *
     * @param data Dados preenchidos pelo usuário na tela de sessão manual.
     */
    override suspend fun createManualSession(
        data: CreateManualSessionData
    ) {
        val currentUserId = "user_4"

        val currentUser = userDao.getUserById(
            userId = currentUserId
        )

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
                id = currentUserId,
                name = currentUser?.name ?: "João Vitor",
                avatarInitials = currentUser?.avatarInitials ?: "JV",
                avatarUrl = currentUser?.avatarUrl ?: ""
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
            userId = currentUserId,
            durationMinutes = data.durationMinutes
        )

        updateGroupProgressAfterSession(
            groupIds = data.groupIds,
            durationMinutes = data.durationMinutes
        )
    }

    /**
     * Atualiza o ranking dos grupos após a criação de uma sessão.
     *
     * Para cada grupo selecionado, o método:
     * - busca o ranking atual;
     * - soma os minutos da nova sessão ao usuário atual;
     * - incrementa a quantidade de dias ativos;
     * - adiciona o usuário ao ranking caso ele ainda não exista;
     * - recalcula as posições com base em minutos totais e dias ativos.
     *
     * A posição do ranking é calculada em ordem decrescente de:
     * 1. total de minutos estudados;
     * 2. quantidade de dias ativos.
     *
     * @param groupIds Grupos que receberam a nova sessão.
     * @param userId Usuário responsável pela sessão.
     * @param durationMinutes Duração da sessão em minutos.
     */
    private suspend fun updateRankingAfterSession(
        groupIds: List<String>,
        userId: String,
        durationMinutes: Int
    ) {
        val currentUser = userDao.getUserById(
            userId = userId
        )

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

            /**
             * Enquanto a autenticação real ainda não está integrada ao backend,
             * o app usa um usuário local padrão.
             *
             * Caso esse usuário ainda não exista no ranking do grupo, ele é
             * inserido com os dados disponíveis no banco local ou com valores
             * padrão temporários.
             */
            if (!userAlreadyInRanking) {
                rankingWithUpdatedUser.add(
                    RankingEntryEntity(
                        id = "${groupId}_$userId",
                        groupId = groupId,

                        userId = userId,
                        userDisplayName = currentUser?.name ?: "João Vitor",
                        username = currentUser?.username ?: "@joaovitor",

                        userEmail = currentUser?.email ?: "",
                        userInstitution = currentUser?.institution ?: "",
                        userCourse = currentUser?.course ?: "",

                        userAvatarInitials = currentUser?.avatarInitials ?: "JV",
                        userAvatarUrl = currentUser?.avatarUrl ?: "",

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

    /**
     * Combina a data escolhida pelo usuário com o horário inicial da sessão.
     *
     * A data recebida vem como timestamp em milissegundos. Ela é convertida para
     * uma data local, e o horário selecionado é aplicado sobre essa data.
     *
     * @param dateMillis Data selecionada pelo usuário em milissegundos.
     * @param startTimeMinutes Horário inicial representado em minutos desde 00:00.
     * @return Timestamp final da data e horário de início da sessão.
     */
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

    /**
     * Atualiza o progresso dos grupos após uma nova sessão.
     *
     * Para cada grupo afetado, os minutos da sessão são somados ao progresso
     * geral do grupo e também ao progresso individual do usuário dentro dele.
     *
     * @param groupIds Grupos que receberam a nova sessão.
     * @param durationMinutes Duração da sessão em minutos.
     */
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