package com.example.projectstudy.data.mapper

import com.example.projectstudy.data.local.entity.UserEntity
import com.example.projectstudy.domain.model.User

/**
 * Converte uma entidade local de usuário para o modelo de domínio.
 *
 * A entidade [UserEntity] representa como os dados do usuário são armazenados
 * no banco local Room. Já o modelo [User] é usado pelas camadas de domínio
 * e interface do app.
 *
 * Essa conversão evita que telas e casos de uso dependam diretamente da
 * estrutura da tabela `users`.
 *
 * @return Usuário convertido para o modelo de domínio.
 */
fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        username = username,
        email = email,
        institution = institution,
        course = course,
        avatarInitials = avatarInitials,
        avatarUrl = avatarUrl,
        streakDays = streakDays,
        totalMinutes = totalMinutes,
        totalActivities = totalActivities
    )
}

/**
 * Converte um usuário do domínio para uma entidade local do Room.
 *
 * Essa função é usada quando o app precisa salvar ou atualizar os dados de um
 * usuário no banco local, seja durante o seed inicial, atualização de perfil
 * ou futura sincronização com uma API remota.
 *
 * O campo [lastSyncedAtMillis] indica o momento da última atualização local
 * ou sincronização. Por padrão, recebe o horário atual.
 *
 * @param lastSyncedAtMillis Momento da última atualização ou sincronização.
 * @return Usuário convertido para entidade local.
 */
fun User.toEntity(
    lastSyncedAtMillis: Long = System.currentTimeMillis()
): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        username = username,
        email = email,
        institution = institution,
        course = course,
        avatarInitials = avatarInitials,
        avatarUrl = avatarUrl,
        totalMinutes = totalMinutes,
        totalActivities = totalActivities,
        streakDays = streakDays,
        lastSyncedAtMillis = lastSyncedAtMillis
    )
}