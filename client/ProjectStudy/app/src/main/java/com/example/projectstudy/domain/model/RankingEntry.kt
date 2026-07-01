package com.example.projectstudy.domain.model

/**
 * Modelo de domínio que representa uma posição no ranking de um grupo.
 *
 * Cada entrada indica o desempenho de um usuário dentro de um grupo específico,
 * considerando informações como posição, tempo total estudado e quantidade de
 * dias ativos.
 *
 * Esse modelo é usado principalmente pela tela de ranking, mas também pode ser
 * reutilizado em cards, resumos de grupo ou destaques do usuário atual.
 */
data class RankingEntry(

    /**
     * Identificador do grupo ao qual esta entrada de ranking pertence.
     */
    val groupId: String,

    /**
     * Usuário associado a esta posição no ranking.
     *
     * Contém os dados necessários para exibir nome, username, avatar e demais
     * informações básicas na interface.
     */
    val user: User,

    /**
     * Posição atual do usuário no ranking do grupo.
     *
     * Quanto menor o valor, melhor a colocação.
     */
    val position: Int,

    /**
     * Total de minutos estudados pelo usuário dentro do grupo.
     *
     * Esse valor geralmente é usado como critério principal de ordenação.
     */
    val totalMinutes: Int,

    /**
     * Quantidade de dias em que o usuário teve atividade no grupo.
     *
     * Pode ser usada como informação complementar ou critério de desempate.
     */
    val activeDays: Int,

    /**
     * Indica se esta entrada representa o usuário atual do app.
     *
     * Quando verdadeiro, a interface pode destacar visualmente essa linha no
     * ranking para facilitar a identificação do usuário logado.
     */
    val isCurrentUser: Boolean = false
)