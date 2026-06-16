package com.example.projectstudy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.projectstudy.data.local.entity.GroupEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO responsável pelas operações de leitura e escrita da tabela de grupos.
 *
 * Os grupos são usados como base para telas como feed, ranking, perfil e
 * registro manual de sessões. Este DAO fornece tanto consultas reativas com
 * [Flow], quanto consultas pontuais usadas por repositórios e processos internos.
 */
@Dao
interface GroupDao {

    /**
     * Observa todos os grupos cadastrados no banco local.
     *
     * Como retorna [Flow], qualquer alteração na tabela `groups` atualiza
     * automaticamente as telas ou ViewModels que estiverem coletando esse fluxo.
     *
     * @return Fluxo com a lista de grupos locais.
     */
    @Query("SELECT * FROM groups")
    fun observeGroups(): Flow<List<GroupEntity>>

    /**
     * Observa um grupo específico pelo ID.
     *
     * Útil quando uma tela precisa reagir automaticamente a mudanças em um grupo,
     * como progresso, meta, número de membros ou posição do usuário no ranking.
     *
     * @param groupId Identificador do grupo buscado.
     * @return Fluxo com o grupo encontrado ou null caso ele não exista.
     */
    @Query("SELECT * FROM groups WHERE id = :groupId LIMIT 1")
    fun observeGroupById(groupId: String): Flow<GroupEntity?>

    /**
     * Busca o primeiro grupo disponível no banco local.
     *
     * Atualmente, o app usa o primeiro grupo como grupo principal do usuário
     * para alimentar telas como feed e ranking.
     *
     * @return Primeiro grupo encontrado ou null caso o banco esteja vazio.
     */
    @Query("SELECT * FROM groups LIMIT 1")
    suspend fun getFirstGroup(): GroupEntity?

    /**
     * Busca todos os grupos de forma pontual.
     *
     * Diferente de [observeGroups], este método não observa mudanças futuras.
     * Ele apenas retorna o estado atual da tabela no momento da chamada.
     *
     * @return Lista atual de grupos salvos localmente.
     */
    @Query("SELECT * FROM groups")
    suspend fun getGroups(): List<GroupEntity>

    /**
     * Busca um grupo específico pelo ID de forma pontual.
     *
     * Esse método é usado quando o repositório precisa ler o valor atual do grupo
     * para aplicar alguma atualização, como incrementar o progresso após uma nova
     * sessão de estudo.
     *
     * @param groupId Identificador do grupo buscado.
     * @return Grupo encontrado ou null caso não exista.
     */
    @Query("SELECT * FROM groups WHERE id = :groupId LIMIT 1")
    suspend fun getGroupByIdOnce(
        groupId: String
    ): GroupEntity?

    /**
     * Insere ou atualiza um único grupo.
     *
     * O Room executa insert quando o grupo ainda não existe e update quando já
     * existe um registro com a mesma chave primária.
     *
     * @param group Grupo que será salvo no banco local.
     */
    @Upsert
    suspend fun upsertGroup(group: GroupEntity)

    /**
     * Insere ou atualiza vários grupos de uma vez.
     *
     * Usado principalmente no seed inicial ou em futuras sincronizações com API,
     * quando vários grupos forem recebidos ao mesmo tempo.
     *
     * @param groups Lista de grupos que serão salvos no banco local.
     */
    @Upsert
    suspend fun upsertGroups(groups: List<GroupEntity>)
}