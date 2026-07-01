package com.example.projectstudy.domain.model

/**
 * Modelo de domínio que representa um grupo de estudos.
 *
 * Esse modelo é usado pelas camadas de domínio e interface para exibir e
 * manipular informações de grupos sem depender diretamente da entidade local
 * do Room ou de uma futura resposta da API.
 *
 * Um grupo concentra dados como nome, descrição, banner, código de convite,
 * quantidade de membros, meta de estudo, progresso atual e informações do
 * usuário no ranking.
 */
data class Group(

    /**
     * Identificador único do grupo.
     *
     * Esse ID é usado para relacionar o grupo com atividades, ranking e sessões
     * de estudo publicadas.
     */
    val id: String,

    /**
     * Nome exibido do grupo.
     */
    val name: String,

    /**
     * Descrição curta do grupo.
     *
     * Pode explicar o objetivo do grupo, disciplina, turma ou contexto de estudo.
     */
    val description: String,

    /**
     * URL ou caminho da imagem de banner do grupo.
     *
     * Usado para compor a apresentação visual do grupo na interface.
     */
    val bannerUrl: String,

    /**
     * Código usado para convite ou entrada no grupo.
     */
    val inviteCode: String,

    /**
     * Quantidade de membros participantes do grupo.
     */
    val memberCount: Int,

    /**
     * Meta de estudo do grupo em minutos.
     *
     * Representa o objetivo coletivo que o grupo pretende alcançar.
     */
    val goalMinutes: Int,

    /**
     * Progresso atual do grupo em minutos.
     *
     * Esse valor é atualizado conforme novas sessões de estudo são registradas
     * no grupo.
     */
    val currentMinutes: Int,

    /**
     * Posição atual do usuário no ranking deste grupo.
     */
    val userRankingPosition: Int,

    /**
     * Total de minutos estudados pelo usuário dentro deste grupo.
     */
    val userMinutes: Int,

    /**
     * Métrica usada para calcular ou ordenar o ranking do grupo.
     *
     * Exemplo: tempo total estudado, dias ativos ou outro critério definido
     * pelo domínio do app.
     */
    val rankingMetric: RankingMetric,

    /**
     * Momento de criação do grupo em milissegundos desde a época Unix.
     */
    val createdAtMillis: Long,

    /**
     * Indica se o grupo está ativo para uso no app.
     *
     * Grupos inativos podem ser ocultados da interface ou ignorados em fluxos
     * como publicação de sessão e listagem de grupos disponíveis.
     */
    val isActive: Boolean = true
)