package com.example.projectstudy.data.mapper

import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity
import com.example.projectstudy.data.local.relation.StudyActivityWithRelations
import com.example.projectstudy.domain.model.ActivityAuthor
import com.example.projectstudy.domain.model.StudyActivity

/**
 * Converte uma atividade carregada do banco local com suas relações para o modelo de domínio.
 *
 * [StudyActivityWithRelations] contém a entidade principal da atividade e os dados
 * relacionados carregados pelo Room, como grupos associados e mídias anexadas.
 *
 * Essa conversão transforma os dados persistidos em [StudyActivity], que é o modelo
 * usado pelas camadas de domínio e interface.
 *
 * @return Atividade de estudo convertida para o modelo de domínio.
 */
fun StudyActivityWithRelations.toDomain(): StudyActivity {
    return StudyActivity(
        id = activity.id,
        groupIds = groups.map { group ->
            group.id
        },
        author = ActivityAuthor(
            id = activity.authorId,
            name = activity.authorName,
            avatarInitials = activity.authorAvatarInitials,
            avatarUrl = activity.authorAvatarUrl
        ),
        title = activity.title,
        subject = activity.subject,
        description = activity.description,
        durationMinutes = activity.durationMinutes,
        imageUrl = activity.imageUrl,
        mediaUris = media
            .sortedBy { item -> item.position }
            .map { item -> item.uri },
        reactions = activity.reactions,
        startedAtMillis = activity.startedAtMillis,
        endedAtMillis = activity.endedAtMillis,
        createdAtMillis = activity.createdAtMillis,
        isManual = activity.isManual
    )
}

/**
 * Converte uma atividade do domínio para uma entidade local do Room.
 *
 * Essa função é usada quando o app precisa salvar ou atualizar uma atividade
 * no banco local, seja por seed inicial, criação manual de sessão ou futura
 * sincronização com API.
 *
 * Como o app segue uma abordagem offline-first, os campos [isSynced] e
 * [pendingSyncAction] controlam se a atividade já foi sincronizada ou se ainda
 * possui alguma ação pendente.
 *
 * @param isSynced Indica se a atividade já está sincronizada com a fonte remota.
 * @param pendingSyncAction Ação pendente de sincronização, como "CREATE", "UPDATE" ou "DELETE".
 * @return Atividade convertida para entidade local.
 */
fun StudyActivity.toEntity(
    isSynced: Boolean = true,
    pendingSyncAction: String? = null
): StudyActivityEntity {
    return StudyActivityEntity(
        id = id,

        authorId = author.id,
        authorName = author.name,
        authorAvatarInitials = author.avatarInitials,
        authorAvatarUrl = author.avatarUrl,

        title = title,
        subject = subject,
        description = description,

        durationMinutes = durationMinutes,
        durationSeconds = durationMinutes * 60,

        imageUrl = imageUrl,

        reactions = reactions,

        startedAtMillis = startedAtMillis,
        endedAtMillis = endedAtMillis,
        createdAtMillis = createdAtMillis,

        isManual = isManual,
        isSynced = isSynced,
        pendingSyncAction = pendingSyncAction
    )
}

/**
 * Converte os IDs dos grupos da atividade em relações para a tabela intermediária.
 *
 * Como uma atividade pode estar vinculada a mais de um grupo, o Room utiliza
 * [ActivityGroupCrossRef] para representar a relação muitos-para-muitos entre
 * atividades e grupos.
 *
 * @return Lista de vínculos entre a atividade e seus grupos.
 */
fun StudyActivity.toGroupRefs(): List<ActivityGroupCrossRef> {
    return groupIds.map { groupId ->
        ActivityGroupCrossRef(
            activityId = id,
            groupId = groupId
        )
    }
}

/**
 * Converte as mídias da atividade em entidades locais.
 *
 * Cada URI presente em [StudyActivity.mediaUris] é transformada em uma
 * [ActivityMediaEntity], mantendo a posição original para preservar a ordem
 * de exibição das mídias na interface.
 *
 * O ID de cada mídia é gerado a partir do ID da atividade e do índice da mídia
 * na lista.
 *
 * @return Lista de mídias convertidas para entidades locais.
 */
fun StudyActivity.toMediaEntities(): List<ActivityMediaEntity> {
    return mediaUris.mapIndexed { index, uri ->
        ActivityMediaEntity(
            id = "${id}_media_$index",
            activityId = id,
            uri = uri,
            position = index
        )
    }
}