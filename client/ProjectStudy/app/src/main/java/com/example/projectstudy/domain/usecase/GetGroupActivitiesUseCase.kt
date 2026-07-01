package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.ActivityRepository
import com.example.projectstudy.domain.model.StudyActivity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso responsável por observar as atividades de um grupo específico.
 *
 * Essa classe centraliza a ação de buscar as sessões publicadas em um grupo,
 * evitando que ViewModels acessem diretamente o [ActivityRepository].
 *
 * É usada principalmente em telas como feed do grupo, onde a interface precisa
 * exibir uma lista reativa de atividades de estudo.
 */
class GetGroupActivitiesUseCase @Inject constructor(

    /**
     * Repositório responsável por fornecer as atividades de estudo.
     */
    private val activityRepository: ActivityRepository
) {

    /**
     * Observa as atividades associadas ao grupo informado.
     *
     * Como retorna [Flow], qualquer alteração nas atividades do grupo será
     * emitida automaticamente para quem estiver coletando esse fluxo.
     *
     * @param groupId Identificador do grupo cujas atividades serão observadas.
     * @return Fluxo com a lista de atividades do grupo.
     */
    operator fun invoke(
        groupId: String
    ): Flow<List<StudyActivity>> {
        return activityRepository.observeActivitiesByGroupId(groupId)
    }
}