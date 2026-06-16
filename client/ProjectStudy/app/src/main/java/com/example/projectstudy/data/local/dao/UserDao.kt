package com.example.projectstudy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.projectstudy.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO responsável pelas operações de leitura e escrita da tabela de usuários.
 *
 * Essa interface concentra as consultas relacionadas ao usuário local do app.
 * Os dados do usuário são usados em telas como perfil, feed, ranking e criação
 * de sessões de estudo.
 *
 * O DAO oferece consultas reativas com [Flow], para observar mudanças no usuário,
 * e consultas pontuais, usadas quando o repositório precisa apenas buscar o estado
 * atual do registro.
 */
@Dao
interface UserDao {

    /**
     * Observa um usuário específico pelo ID.
     *
     * Como retorna [Flow], qualquer alteração no registro do usuário será emitida
     * automaticamente para quem estiver coletando esse fluxo.
     *
     * Esse método é útil para telas que precisam reagir a mudanças nos dados do
     * perfil, estatísticas ou avatar do usuário.
     *
     * @param userId Identificador do usuário buscado.
     * @return Fluxo com o usuário encontrado ou null caso ele não exista.
     */
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun observeUserById(userId: String): Flow<UserEntity?>

    /**
     * Busca um usuário específico pelo ID de forma pontual.
     *
     * Diferente de [observeUserById], este método não observa mudanças futuras.
     * Ele retorna apenas o estado atual do usuário no momento da chamada.
     *
     * É usado por repositórios que precisam montar dados locais, como autor de
     * uma sessão manual ou entrada no ranking.
     *
     * @param userId Identificador do usuário buscado.
     * @return Usuário encontrado ou null caso ele não exista.
     */
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserEntity?

    /**
     * Insere ou atualiza um único usuário.
     *
     * O Room executa insert quando o usuário ainda não existe e update quando
     * já existe um registro com a mesma chave primária.
     *
     * @param user Usuário que será salvo no banco local.
     */
    @Upsert
    suspend fun upsertUser(user: UserEntity)

    /**
     * Insere ou atualiza múltiplos usuários.
     *
     * Esse método é útil para o seed inicial ou para uma futura sincronização
     * com API, quando vários usuários forem carregados de uma vez.
     *
     * @param users Lista de usuários que serão salvos no banco local.
     */
    @Upsert
    suspend fun upsertUsers(users: List<UserEntity>)
}