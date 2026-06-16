package com.example.projectstudy.data.local.repository

import com.example.projectstudy.data.local.LocalDataSeeder
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.mapper.toDomain
import com.example.projectstudy.data.repository.RankingRepository
import com.example.projectstudy.domain.model.RankingEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementação local do repositório de ranking.
 *
 * Essa classe fornece o ranking de participantes de um grupo a partir do banco
 * local. Ela é usada principalmente pela tela de ranking e também pode ser
 * reutilizada por resumos exibidos no feed ou em outras telas.
 *
 * Antes de observar os dados, o repositório garante que o seed inicial foi
 * executado por meio do [LocalDataSeeder]. Isso permite que o app tenha dados
 * locais disponíveis mesmo antes da integração com uma API.
 *
 * Como o ranking é exposto por [Flow], qualquer alteração feita no banco local,
 * como a publicação de uma nova sessão de estudo, atualiza automaticamente as
 * telas que estiverem observando esses dados.
 */
class LocalRankingRepository @Inject constructor(
    private val rankingDao: RankingDao,
    private val localDataSeeder: LocalDataSeeder
) : RankingRepository {

    /**
     * Observa o ranking de um grupo específico.
     *
     * O DAO retorna entidades locais do Room, e este repositório converte cada
     * entrada para o modelo de domínio usado pela camada de UI.
     *
     * @param groupId Identificador do grupo cujo ranking será observado.
     * @return Fluxo com a lista de participantes do ranking convertida para domínio.
     */
    override fun observeRankingByGroupId(
        groupId: String
    ): Flow<List<RankingEntry>> {
        return flow {
            localDataSeeder.seedIfNeeded()

            emitAll(
                rankingDao.observeRankingByGroupId(groupId)
                    .map { entries ->
                        entries.map { entry ->
                            entry.toDomain()
                        }
                    }
            )
        }
    }
}