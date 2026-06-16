package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.GroupRepository
import com.example.projectstudy.domain.model.Group
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * Caso de uso responsável por observar o grupo principal do usuário atual.
 *
 * Essa classe centraliza a ação de obter o primeiro grupo associado ao usuário,
 * evitando que ViewModels acessem diretamente o [GroupRepository].
 *
 * Atualmente, esse grupo principal é usado como base para telas como:
 * - feed de atividades;
 * - ranking;
 * - criação manual de sessão;
 * - informações gerais do grupo.
 */
class GetFirstUserGroupUseCase @Inject constructor(

    /**
     * Repositório responsável por fornecer os grupos do usuário.
     */
    private val groupRepository: GroupRepository
) {

    /**
     * Observa o primeiro grupo associado ao usuário atual.
     *
     * Como retorna [Flow], qualquer alteração no grupo salvo localmente será
     * emitida automaticamente para quem estiver coletando esse fluxo.
     *
     * @return Fluxo com o grupo principal do usuário.
     */
    operator fun invoke(): Flow<Group> {
        return groupRepository.observeFirstUserGroup()
    }
}