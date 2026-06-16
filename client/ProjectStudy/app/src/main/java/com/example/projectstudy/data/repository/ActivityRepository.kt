package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.StudyActivity
import kotlinx.coroutines.flow.Flow

/**
 * Contrato responsável por fornecer atividades de estudo para a camada de domínio.
 *
 * O repositório abstrai a origem dos dados, permitindo que os casos de uso e
 * ViewModels acessem atividades sem depender diretamente do Room, de uma API
 * remota ou de qualquer implementação específica.
 *
 * Como o app segue uma abordagem offline-first, as implementações podem buscar
 * os dados inicialmente no banco local e, futuramente, sincronizá-los com uma
 * fonte remota.
 */
interface ActivityRepository {

    /**
     * Observa as atividades associadas a um grupo específico.
     *
     * Como retorna [Flow], qualquer alteração nas atividades do grupo é emitida
     * automaticamente para quem estiver observando esse fluxo, mantendo telas
     * como o feed sempre atualizadas.
     *
     * @param groupId Identificador do grupo cujas atividades serão observadas.
     * @return Fluxo com a lista de atividades do grupo.
     */
    fun observeActivitiesByGroupId(
        groupId: String
    ): Flow<List<StudyActivity>>

    /**
     * Observa as atividades criadas por um usuário específico.
     *
     * Esse método é usado principalmente em telas como perfil ou histórico,
     * onde o app precisa exibir apenas as sessões publicadas por determinado
     * usuário.
     *
     * @param userId Identificador do usuário autor das atividades.
     * @return Fluxo com a lista de atividades do usuário.
     */
    fun observeActivitiesByUserId(
        userId: String
    ): Flow<List<StudyActivity>>
}