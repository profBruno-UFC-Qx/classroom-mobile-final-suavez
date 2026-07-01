package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.remote.api.GroupApi
import com.example.projectstudy.data.remote.mapper.toEntity
import com.example.projectstudy.data.repository.GroupRepository
import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.NoUserGroupException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementação local do repositório de grupos.
 *
 * Essa classe é responsável por fornecer os grupos salvos no banco local.
 * Antes de observar os dados, ela garante que o seed inicial foi executado,
 * permitindo que o app tenha grupos disponíveis mesmo sem depender de API.
 *
 * Os métodos retornam [Flow], então as telas são atualizadas automaticamente
 * sempre que os dados dos grupos forem alterados no Room.
 */
class LocalGroupRepository @Inject constructor(
    private val groupDao: GroupDao,
    private val groupApi: GroupApi
) : GroupRepository {

    /**
     * Observa o primeiro grupo associado ao usuário.
     *
     * Atualmente, o app trabalha com o primeiro grupo local como grupo principal
     * do usuário. Esse grupo é usado como base para telas como feed, ranking e
     * criação manual de sessão.
     *
     * Caso nenhum grupo exista no banco local, [NoUserGroupException] é lançada
     * para que a interface possa oferecer um fluxo de entrada em grupo.
     *
     * @return Fluxo com o primeiro grupo convertido para o modelo de domínio.
     */
    override fun observeFirstUserGroup(): Flow<Group> {
        return groupDao.observeGroups()
            .map { groups ->
                val entity = groups.firstOrNull() ?: throw NoUserGroupException()

                entity.toDomain()
            }
    }

    /**
     * Observa todos os grupos disponíveis para o usuário.
     *
     * Esse método pode ser usado em telas de seleção de grupo, listagem de grupos
     * ou formulários em que o usuário precisa escolher onde publicar uma sessão.
     *
     * @return Fluxo com a lista de grupos convertidos para o modelo de domínio.
     */
    override fun observeUserGroups(): Flow<List<Group>> {
        return groupDao.observeGroups()
            .map { groups ->
                groups.map { group ->
                    group.toDomain()
                }
            }
    }

    /**
     * Entra em um grupo existente pelo código de convite e salva o resultado
     * localmente, fazendo com que ele passe a aparecer nos fluxos observados
     * por [observeFirstUserGroup] e [observeUserGroups].
     */
    override suspend fun joinGroup(
        token: String,
        inviteCode: String
    ): Result<Unit> {
        return runCatching {
            val response = groupApi.join(
                token = token,
                inviteCode = inviteCode
            )

            groupDao.upsertGroup(
                response.toEntity(
                    serverTimestamp = System.currentTimeMillis()
                )
            )
        }
    }
}