package com.example.projectstudy.data.mapper

import com.example.projectstudy.data.local.entity.GroupEntity
import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.RankingMetric

/**
 * Converte uma entidade local de grupo para o modelo de domínio.
 *
 * A entidade [GroupEntity] representa como o grupo é salvo no banco local Room.
 * Já o modelo [Group] é usado pelas camadas de domínio e interface.
 *
 * Essa conversão é necessária para evitar que a UI dependa diretamente da
 * estrutura do banco local.
 *
 * @return Grupo convertido para o modelo de domínio.
 */
fun GroupEntity.toDomain(): Group {
    return Group(
        id = id,
        name = name,
        description = description,
        bannerUrl = bannerUrl,
        memberCount = memberCount,
        inviteCode = inviteCode,
        goalMinutes = goalMinutes,
        currentMinutes = currentMinutes,
        userRankingPosition = userRankingPosition,
        userMinutes = userMinutes,
        rankingMetric = rankingMetric.toRankingMetric(),
        createdAtMillis = createdAtMillis
    )
}

/**
 * Converte um grupo do domínio para uma entidade local do Room.
 *
 * Essa função é usada quando o app precisa salvar ou atualizar grupos no banco
 * local. Ela transforma o modelo [Group], usado pela lógica do app, em
 * [GroupEntity], usado pela camada de persistência.
 *
 * O campo [lastSyncedAtMillis] indica o momento da última atualização local ou
 * sincronização. Por padrão, recebe o horário atual.
 *
 * @param lastSyncedAtMillis Momento da última atualização ou sincronização.
 * @return Grupo convertido para entidade local.
 */
fun Group.toEntity(
    lastSyncedAtMillis: Long = System.currentTimeMillis()
): GroupEntity {
    return GroupEntity(
        id = id,
        name = name,
        description = description,
        bannerUrl = bannerUrl,
        memberCount = memberCount,
        inviteCode = inviteCode,
        goalMinutes = goalMinutes,
        currentMinutes = currentMinutes,
        userRankingPosition = userRankingPosition,
        userMinutes = userMinutes,
        rankingMetric = rankingMetric.name,
        createdAtMillis = createdAtMillis,
        lastSyncedAtMillis = lastSyncedAtMillis
    )
}

/**
 * Converte uma String salva no banco para o enum [RankingMetric].
 *
 * No banco local, a métrica do ranking é armazenada como texto para facilitar
 * a persistência. Na camada de domínio, ela é usada como enum.
 *
 * Caso o valor salvo no banco não corresponda a nenhuma opção válida do enum,
 * a função retorna a primeira métrica disponível como fallback, evitando falhas
 * por dados inválidos ou antigos.
 *
 * @return Métrica de ranking correspondente ao texto salvo.
 */
private fun String.toRankingMetric(): RankingMetric {
    return enumValues<RankingMetric>()
        .firstOrNull { metric ->
            metric.name == this
        }
        ?: enumValues<RankingMetric>().first()
}