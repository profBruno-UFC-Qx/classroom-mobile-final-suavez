package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.ActivityRepository
import com.example.projectstudy.domain.model.StudyActivity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso responsável por observar as atividades de um usuário específico.
 *
 * Essa classe centraliza a ação de buscar as sessões publicadas por um usuário,
 * evitando que ViewModels acessem diretamente o [ActivityRepository].
 *
 * É usada principalmente em telas como perfil ou histórico pessoal, onde a
 * interface precisa exibir as atividades criadas por determinado usuário.
 */
class GetUserActivitiesUseCase @Inject constructor(

    /**
     * Repositório responsável por fornecer as atividades de estudo.
     */
    private val activityRepository: ActivityRepository
) {

    /**
     * Observa as atividades criadas pelo usuário informado.
     *
     * Como retorna [Flow], qualquer alteração nas atividades desse usuário será
     * emitida automaticamente para quem estiver coletando esse fluxo.
     *
     * @param userId Identificador do usuário cujas atividades serão observadas.
     * @return Fluxo com a lista de atividades criadas pelo usuário.
     */
    operator fun invoke(
        userId: String
    ): Flow<List<StudyActivity>> {
        return activityRepository.observeActivitiesByUserId(userId)
    }
}