package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.RankingRepository
import com.example.projectstudy.domain.model.RankingEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso responsável por observar o ranking de um grupo específico.
 *
 * Essa classe centraliza a ação de buscar a classificação dos usuários de um
 * grupo, evitando que ViewModels acessem diretamente o [RankingRepository].
 *
 * É usada principalmente pela tela de ranking, permitindo que a interface
 * acompanhe alterações locais após novas sessões de estudo serem registradas.
 */
class GetGroupRankingUseCase @Inject constructor(

    /**
     * Repositório responsável por fornecer as entradas de ranking.
     */
    private val rankingRepository: RankingRepository
) {

    /**
     * Observa o ranking associado ao grupo informado.
     *
     * Como retorna [Flow], qualquer alteração nas entradas de ranking do grupo
     * será emitida automaticamente para quem estiver coletando esse fluxo.
     *
     * @param groupId Identificador do grupo cujo ranking será observado.
     * @return Fluxo com a lista de entradas do ranking do grupo.
     */
    operator fun invoke(
        groupId: String
    ): Flow<List<RankingEntry>> {
        return rankingRepository.observeRankingByGroupId(groupId)
    }
}