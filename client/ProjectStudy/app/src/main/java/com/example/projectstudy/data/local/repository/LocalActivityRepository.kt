package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.ActivityRepository
import com.example.projectstudy.domain.model.StudyActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementação local do repositório de atividades de estudo.
 *
 * Essa classe representa a fonte local de dados para as atividades exibidas
 * no feed, no ranking e no perfil do usuário.
 *
 * Antes de observar os dados do banco, o repositório garante que o seed inicial
 * foi executado por meio do [LocalDataSeeder]. Isso permite que o app tenha
 * dados locais disponíveis mesmo sem integração inicial com API.
 *
 * As consultas retornam [Flow], então qualquer alteração feita no banco local
 * é refletida automaticamente nas telas que estiverem observando esses dados.
 */
class LocalActivityRepository @Inject constructor(
    private val studyActivityDao: StudyActivityDao
) : ActivityRepository {

    /**
     * Observa as atividades associadas a um grupo específico.
     *
     * Esse método é usado principalmente no feed do grupo. Como o retorno é um
     * [Flow], a tela é atualizada automaticamente quando uma nova sessão é salva
     * localmente ou quando alguma atividade existente é modificada.
     *
     * @param groupId Identificador do grupo cujas atividades serão observadas.
     * @return Fluxo com a lista de atividades convertidas para o modelo de domínio.
     */
    override fun observeActivitiesByGroupId(
        groupId: String
    ): Flow<List<StudyActivity>> {
        return studyActivityDao.observeActivitiesByGroupId(groupId)
            .map { activities ->
                activities.map { activity ->
                    activity.toDomain()
                }
            }
    }

    /**
     * Observa as atividades publicadas por um usuário específico.
     *
     * Esse método é útil para telas como perfil, histórico pessoal ou qualquer
     * listagem que precise mostrar apenas as sessões de estudo de um usuário.
     *
     * @param userId Identificador do usuário cujas atividades serão observadas.
     * @return Fluxo com a lista de atividades convertidas para o modelo de domínio.
     */
    override fun observeActivitiesByUserId(
        userId: String
    ): Flow<List<StudyActivity>> {
        return studyActivityDao.observeActivitiesByUserId(userId)
            .map { activities ->
                activities.map { activity ->
                    activity.toDomain()
                }
            }
    }
}