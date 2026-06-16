package com.example.projectstudy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.projectstudy.data.local.entity.RankingEntryEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO responsável pelas operações de leitura e escrita da tabela de ranking.
 *
 * O ranking é calculado por grupo e representa a classificação dos usuários
 * com base em métricas como tempo total estudado, dias ativos e posição.
 *
 * Este DAO fornece consultas reativas com [Flow], usadas pela interface, e
 * consultas pontuais, usadas por repositórios durante atualizações locais.
 */
@Dao
interface RankingDao {

    /**
     * Observa o ranking de um grupo específico.
     *
     * Como retorna [Flow], qualquer alteração na tabela `ranking_entries`
     * atualiza automaticamente as telas que estiverem observando o ranking,
     * como a tela principal de classificação e possíveis resumos no feed.
     *
     * Os resultados são ordenados pela posição atual do usuário no ranking.
     *
     * @param groupId Identificador do grupo cujo ranking será observado.
     * @return Fluxo com a lista de entradas do ranking ordenadas por posição.
     */
    @Query(
        """
        SELECT * FROM ranking_entries
        WHERE groupId = :groupId
        ORDER BY position ASC
        """
    )
    fun observeRankingByGroupId(
        groupId: String
    ): Flow<List<RankingEntryEntity>>

    /**
     * Busca o ranking atual de um grupo de forma pontual.
     *
     * Diferente de [observeRankingByGroupId], este método não observa mudanças
     * futuras. Ele retorna apenas o estado atual do ranking no momento da chamada.
     *
     * É usado principalmente durante a criação de uma nova sessão de estudo,
     * quando o repositório precisa recalcular minutos, dias ativos e posições.
     *
     * @param groupId Identificador do grupo cujo ranking será buscado.
     * @return Lista atual de entradas do ranking ordenadas por posição.
     */
    @Query(
        """
        SELECT * FROM ranking_entries
        WHERE groupId = :groupId
        ORDER BY position ASC
        """
    )
    suspend fun getRankingByGroupIdOnce(
        groupId: String
    ): List<RankingEntryEntity>

    /**
     * Insere ou atualiza múltiplas entradas de ranking.
     *
     * O Room executa insert para entradas novas e update para entradas que já
     * possuem a mesma chave primária. Esse método é usado após recalcular a
     * classificação de um grupo.
     *
     * @param entries Lista de entradas de ranking que serão salvas no banco local.
     */
    @Upsert
    suspend fun upsertRanking(
        entries: List<RankingEntryEntity>
    )

    /**
     * Remove todas as entradas de ranking associadas a um grupo.
     *
     * Esse método pode ser usado em processos de seed, reset local ou futura
     * sincronização com API, quando for necessário substituir completamente o
     * ranking de um grupo.
     *
     * @param groupId Identificador do grupo cujo ranking será removido.
     */
    @Query("DELETE FROM ranking_entries WHERE groupId = :groupId")
    suspend fun deleteRankingByGroupId(
        groupId: String
    )
}