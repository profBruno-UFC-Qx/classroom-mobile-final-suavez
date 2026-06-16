package com.example.projectstudy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity
import com.example.projectstudy.data.local.relation.StudyActivityWithRelations
import kotlinx.coroutines.flow.Flow

/**
 * DAO responsável pelas operações de leitura e escrita das atividades de estudo.
 *
 * Esse DAO manipula a tabela principal de atividades e também as tabelas
 * relacionadas a grupos e mídias:
 * - `study_activities`;
 * - `activity_group_cross_refs`;
 * - `activity_media`.
 *
 * As consultas principais retornam [Flow], permitindo que telas como feed e
 * perfil sejam atualizadas automaticamente quando novas sessões forem salvas
 * no banco local.
 */
@Dao
interface StudyActivityDao {

    /**
     * Observa as atividades publicadas em um grupo específico.
     *
     * A consulta usa um INNER JOIN com a tabela intermediária
     * `activity_group_cross_refs`, pois uma atividade pode estar associada
     * a vários grupos.
     *
     * O uso de [Transaction] garante que a atividade e suas relações, como
     * grupos e mídias, sejam carregadas de forma consistente pelo Room.
     *
     * Os resultados são ordenados da atividade mais recente para a mais antiga.
     *
     * @param groupId Identificador do grupo cujas atividades serão observadas.
     * @return Fluxo com atividades e suas relações carregadas.
     */
    @Transaction
    @Query(
        """
        SELECT study_activities.*
        FROM study_activities
        INNER JOIN activity_group_cross_refs
            ON study_activities.id = activity_group_cross_refs.activityId
        WHERE activity_group_cross_refs.groupId = :groupId
        ORDER BY study_activities.startedAtMillis DESC
        """
    )
    fun observeActivitiesByGroupId(
        groupId: String
    ): Flow<List<StudyActivityWithRelations>>

    /**
     * Observa as atividades criadas por um usuário específico.
     *
     * Essa consulta é usada principalmente em telas como perfil ou histórico
     * pessoal, onde o app precisa exibir apenas as sessões publicadas por um
     * determinado usuário.
     *
     * O uso de [Transaction] permite que o Room carregue também as relações da
     * atividade, como grupos associados e mídias anexadas.
     *
     * @param userId Identificador do autor das atividades.
     * @return Fluxo com atividades do usuário e suas relações carregadas.
     */
    @Transaction
    @Query(
        """
        SELECT study_activities.*
        FROM study_activities
        WHERE authorId = :userId
        ORDER BY startedAtMillis DESC
        """
    )
    fun observeActivitiesByUserId(
        userId: String
    ): Flow<List<StudyActivityWithRelations>>

    /**
     * Busca atividades que ainda não foram sincronizadas.
     *
     * Esse método será útil em um fluxo offline-first com API, no qual atividades
     * criadas localmente precisam ser enviadas posteriormente para o servidor.
     *
     * Os resultados são ordenados pela data de criação, do registro mais antigo
     * para o mais recente, preservando a ordem natural de sincronização.
     *
     * @return Lista de atividades pendentes de sincronização.
     */
    @Query(
        """
        SELECT *
        FROM study_activities
        WHERE isSynced = 0
        ORDER BY createdAtMillis ASC
        """
    )
    suspend fun getPendingSyncActivities(): List<StudyActivityEntity>

    /**
     * Insere ou atualiza uma atividade de estudo.
     *
     * O Room executa insert quando a atividade ainda não existe e update quando
     * já existe uma atividade com a mesma chave primária.
     *
     * @param activity Atividade que será salva no banco local.
     */
    @Upsert
    suspend fun upsertActivity(
        activity: StudyActivityEntity
    )

    /**
     * Insere ou atualiza múltiplas atividades de estudo.
     *
     * Usado principalmente em seed inicial ou em futuras sincronizações com API,
     * quando várias atividades forem carregadas de uma vez.
     *
     * @param activities Lista de atividades que serão salvas no banco local.
     */
    @Upsert
    suspend fun upsertActivities(
        activities: List<StudyActivityEntity>
    )

    /**
     * Insere ou atualiza os vínculos entre atividades e grupos.
     *
     * Esses vínculos são salvos na tabela intermediária
     * `activity_group_cross_refs`, permitindo que uma mesma atividade esteja
     * associada a mais de um grupo.
     *
     * @param refs Lista de relações entre atividades e grupos.
     */
    @Upsert
    suspend fun upsertActivityGroupRefs(
        refs: List<ActivityGroupCrossRef>
    )

    /**
     * Insere ou atualiza mídias anexadas às atividades.
     *
     * Cada mídia pertence a uma atividade específica e possui uma posição para
     * preservar a ordem de exibição definida pelo usuário.
     *
     * @param media Lista de mídias que serão salvas no banco local.
     */
    @Upsert
    suspend fun upsertMedia(
        media: List<ActivityMediaEntity>
    )

    /**
     * Remove todos os vínculos de grupo de uma atividade.
     *
     * Esse método é usado antes de recriar as relações de uma atividade, evitando
     * vínculos duplicados ou antigos quando a atividade é atualizada.
     *
     * @param activityId Identificador da atividade cujos vínculos serão removidos.
     */
    @Query("DELETE FROM activity_group_cross_refs WHERE activityId = :activityId")
    suspend fun deleteGroupRefsByActivityId(
        activityId: String
    )

    /**
     * Remove todas as mídias associadas a uma atividade.
     *
     * Esse método é usado antes de salvar novamente as mídias de uma atividade,
     * garantindo que anexos antigos não permaneçam no banco após uma atualização.
     *
     * @param activityId Identificador da atividade cujas mídias serão removidas.
     */
    @Query("DELETE FROM activity_media WHERE activityId = :activityId")
    suspend fun deleteMediaByActivityId(
        activityId: String
    )
}