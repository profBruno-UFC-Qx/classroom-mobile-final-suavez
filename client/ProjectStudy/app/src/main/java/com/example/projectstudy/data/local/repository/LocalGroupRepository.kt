package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.GroupRepository
import com.example.projectstudy.domain.model.Group
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
    private val groupDao: GroupDao
) : GroupRepository {

    /**
     * Observa o primeiro grupo associado ao usuário.
     *
     * Atualmente, o app trabalha com o primeiro grupo local como grupo principal
     * do usuário. Esse grupo é usado como base para telas como feed, ranking e
     * criação manual de sessão.
     *
     * Caso nenhum grupo exista no banco local, uma exceção é lançada para indicar
     * que houve falha no seed ou inconsistência nos dados locais.
     *
     * @return Fluxo com o primeiro grupo convertido para o modelo de domínio.
     */
    override fun observeFirstUserGroup(): Flow<Group> {
        return groupDao.observeGroups()
            .map { groups ->
                requireNotNull(groups.firstOrNull()) {
                    "Nenhum grupo sincronizado encontrado no banco local."
                }.toDomain()
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
}