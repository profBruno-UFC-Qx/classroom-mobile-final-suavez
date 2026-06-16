package com.example.projectstudy.domain.model

/**
 * Modelo de domínio que representa um usuário do app.
 *
 * Esse modelo é usado pelas camadas de domínio e interface para exibir dados
 * do usuário sem depender diretamente da entidade local do Room ou de uma
 * futura resposta da API.
 *
 * Ele concentra informações básicas de identificação, dados acadêmicos,
 * informações de avatar e estatísticas gerais de estudo.
 */
data class User(

    /**
     * Identificador único do usuário.
     *
     * Esse ID é usado para relacionar o usuário com atividades, ranking,
     * perfil e demais dados associados à conta.
     */
    val id: String,

    /**
     * Nome completo ou nome de exibição do usuário.
     */
    val name: String,

    /**
     * Nome de usuário usado como identificador curto no app.
     *
     * Pode ser exibido em telas como ranking, perfil e cards de atividade.
     */
    val username: String,

    /**
     * Email associado ao usuário.
     *
     * Por padrão, permanece vazio quando essa informação ainda não estiver
     * disponível localmente.
     */
    val email: String = "",

    /**
     * Instituição de ensino associada ao usuário.
     *
     * Esse campo ainda pode ser ajustado conforme a definição final do cadastro
     * ou do perfil do usuário.
     */
    val institution: String = "",

    /**
     * Curso associado ao usuário.
     *
     * Esse campo ainda pode ser ajustado conforme a definição final do cadastro
     * ou do perfil do usuário.
     */
    val course: String = "",

    /**
     * Iniciais usadas no avatar quando o usuário não possui imagem de perfil.
     */
    val avatarInitials: String,

    /**
     * URL da imagem de avatar do usuário.
     *
     * Quando estiver vazia, a interface deve exibir um avatar gerado com as
     * iniciais do usuário.
     */
    val avatarUrl: String = "",

    /**
     * Sequência atual de dias consecutivos de estudo.
     *
     * Usado para exibir progresso, engajamento ou mecânicas de gamificação.
     */
    val streakDays: Int = 0,

    /**
     * Total de minutos estudados pelo usuário.
     *
     * Esse valor pode ser usado em estatísticas do perfil e no ranking.
     */
    val totalMinutes: Int = 0,

    /**
     * Quantidade total de atividades ou sessões registradas pelo usuário.
     */
    val totalActivities: Int = 0
)