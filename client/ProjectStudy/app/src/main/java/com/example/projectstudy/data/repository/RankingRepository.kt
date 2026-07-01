package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.RankingEntry
import kotlinx.coroutines.flow.Flow

/**
 * Contrato responsável por fornecer dados de ranking para a camada de domínio.
 *
 * O repositório abstrai a origem dos dados, permitindo que ViewModels e casos
 * de uso acessem o ranking sem depender diretamente do Room, de uma API remota
 * ou de qualquer implementação específica.
 *
 * Como o app segue uma abordagem offline-first, a implementação pode observar
 * o ranking salvo localmente e, futuramente, sincronizar esses dados com uma
 * fonte remota.
 */
interface RankingRepository {

    /**
     * Observa o ranking de um grupo específico.
     *
     * Como retorna [Flow], qualquer alteração nas entradas do ranking será
     * emitida automaticamente para quem estiver observando esse fluxo.
     *
     * Esse método é usado principalmente pela tela de ranking, permitindo que
     * a classificação seja atualizada após novas sessões de estudo serem
     * registradas localmente.
     *
     * @param groupId Identificador do grupo cujo ranking será observado.
     * @return Fluxo com a lista de entradas do ranking do grupo.
     */
    fun observeRankingByGroupId(
        groupId: String
    ): Flow<List<RankingEntry>>
}