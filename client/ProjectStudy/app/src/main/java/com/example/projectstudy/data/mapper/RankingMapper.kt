package com.example.projectstudy.data.mapper

import com.example.projectstudy.data.local.entity.RankingEntryEntity
import com.example.projectstudy.domain.model.RankingEntry
import com.example.projectstudy.domain.model.User

/**
 * Converte uma entrada de ranking salva no banco local para o modelo de domínio.
 *
 * A entidade [RankingEntryEntity] armazena os dados do ranking no formato usado
 * pelo Room. Já [RankingEntry] é o modelo utilizado pela camada de domínio e
 * pela interface.
 *
 * Além dos dados de classificação, essa conversão também monta um objeto [User]
 * com as informações básicas salvas dentro da própria entrada de ranking.
 * Isso permite que a tela de ranking exiba nome, username, curso, instituição
 * e avatar sem precisar consultar a tabela de usuários separadamente.
 *
 * @return Entrada de ranking convertida para o modelo de domínio.
 */
fun RankingEntryEntity.toDomain(): RankingEntry {
    return RankingEntry(
        groupId = groupId,
        user = User(
            id = userId,
            name = userDisplayName,
            username = username,
            email = userEmail,
            institution = userInstitution,
            course = userCourse,
            avatarInitials = userAvatarInitials,
            avatarUrl = userAvatarUrl
        ),
        totalMinutes = totalMinutes,
        activeDays = activeDays,
        position = position,
        isCurrentUser = isCurrentUser
    )
}

/**
 * Converte uma entrada de ranking do domínio para uma entidade local do Room.
 *
 * Essa função é usada quando o app precisa salvar ou atualizar o ranking no
 * banco local, por exemplo após a criação de uma nova sessão de estudo.
 *
 * O ID da entidade é gerado a partir da combinação entre `groupId` e `user.id`,
 * garantindo que cada usuário tenha apenas uma entrada de ranking por grupo.
 *
 * @param lastSyncedAtMillis Momento da última atualização ou sincronização.
 * @return Entrada de ranking convertida para entidade local.
 */
fun RankingEntry.toEntity(
    lastSyncedAtMillis: Long = System.currentTimeMillis()
): RankingEntryEntity {
    return RankingEntryEntity(
        id = "${groupId}_${user.id}",
        groupId = groupId,

        userId = user.id,
        userDisplayName = user.name,
        username = user.username,

        userEmail = user.email,
        userInstitution = user.institution,
        userCourse = user.course,

        userAvatarInitials = user.avatarInitials,
        userAvatarUrl = user.avatarUrl,

        totalMinutes = totalMinutes,
        activeDays = activeDays,
        position = position,
        isCurrentUser = isCurrentUser,

        lastSyncedAtMillis = lastSyncedAtMillis
    )
}