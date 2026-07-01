package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.GroupRepository
import com.example.projectstudy.domain.model.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso responsável por observar todos os grupos do usuário atual.
 *
 * Essa classe centraliza a ação de buscar os grupos associados ao usuário,
 * evitando que ViewModels acessem diretamente o [GroupRepository].
 *
 * É usada em fluxos onde a interface precisa listar os grupos disponíveis,
 * como seleção de grupo para publicação de sessão, telas de grupos ou futuras
 * áreas de gerenciamento.
 */
class GetUserGroupsUseCase @Inject constructor(

    /**
     * Repositório responsável por fornecer os grupos do usuário.
     */
    private val groupRepository: GroupRepository
) {

    /**
     * Observa todos os grupos associados ao usuário atual.
     *
     * Como retorna [Flow], qualquer alteração na lista de grupos será emitida
     * automaticamente para quem estiver coletando esse fluxo.
     *
     * @return Fluxo com a lista de grupos do usuário.
     */
    operator fun invoke(): Flow<List<Group>> {
        return groupRepository.observeUserGroups()
    }
}