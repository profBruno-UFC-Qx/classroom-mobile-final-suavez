package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade local que representa uma entrada no ranking de um grupo.
 *
 * Cada registro armazena o desempenho de um usuário dentro de um grupo específico,
 * incluindo tempo total estudado, dias ativos e posição atual no ranking.
 *
 * Essa entidade também mantém uma cópia dos dados básicos do usuário, como nome,
 * username, instituição, curso e avatar. Isso facilita a exibição do ranking sem
 * precisar buscar o usuário separadamente em outra tabela.
 *
 * A tela de ranking utiliza esses dados para montar a classificação geral e o
 * destaque dos primeiros colocados.
 */
@Entity(tableName = "ranking_entries")
data class RankingEntryEntity(

    /**
     * Identificador único da entrada no ranking.
     *
     * Normalmente pode ser formado pela combinação entre o ID do grupo e o ID
     * do usuário, garantindo que cada usuário tenha apenas uma entrada por grupo.
     */
    @PrimaryKey
    val id: String,

    /**
     * Identificador do grupo ao qual esta entrada de ranking pertence.
     */
    val groupId: String,

    /**
     * Identificador do usuário ranqueado.
     */
    val userId: String,

    /**
     * Nome exibido do usuário no ranking.
     */
    val userDisplayName: String,

    /**
     * Username do usuário.
     *
     * Usado como identificação curta nas listas e cards de ranking.
     */
    val username: String,

    /**
     * Email do usuário.
     *
     * Mantido localmente para preservar os dados básicos do usuário no ranking.
     */
    val userEmail: String,

    /**
     * Instituição associada ao usuário.
     */
    val userInstitution: String,

    /**
     * Curso associado ao usuário.
     */
    val userCourse: String,

    /**
     * Iniciais usadas no avatar quando o usuário não possui imagem.
     */
    val userAvatarInitials: String,

    /**
     * URL da imagem de avatar do usuário.
     *
     * Quando estiver vazia, a interface deve exibir o avatar gerado pelas iniciais.
     */
    val userAvatarUrl: String,

    /**
     * Total de minutos estudados pelo usuário dentro do grupo.
     *
     * Esse campo é usado como critério principal de ordenação do ranking.
     */
    val totalMinutes: Int,

    /**
     * Quantidade de dias ativos do usuário no grupo.
     *
     * Esse campo pode ser usado como critério secundário de desempate no ranking.
     */
    val activeDays: Int,

    /**
     * Posição atual do usuário no ranking.
     *
     * A posição é recalculada sempre que o ranking é atualizado após uma nova
     * sessão de estudo.
     */
    val position: Int,

    /**
     * Indica se esta entrada representa o usuário atual do app.
     *
     * Esse campo permite que a interface destaque visualmente o usuário logado
     * dentro da lista do ranking.
     */
    val isCurrentUser: Boolean,

    /**
     * Momento da última sincronização ou atualização local relevante.
     *
     * Esse valor ajuda a controlar alterações feitas localmente e pode ser usado
     * futuramente em um fluxo de sincronização com API.
     */
    val lastSyncedAtMillis: Long
)