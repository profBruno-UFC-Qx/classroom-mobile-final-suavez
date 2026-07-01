package com.example.projectstudy.domain.model

/**
 * Modelo de domínio que representa o autor de uma atividade de estudo.
 *
 * Esse modelo concentra apenas as informações necessárias para identificar e
 * exibir o autor em telas como feed, perfil, ranking ou detalhes de uma sessão.
 *
 * Ele é usado dentro de [StudyActivity], evitando que a atividade dependa
 * diretamente do modelo completo de usuário ou da entidade local do banco.
 */
data class ActivityAuthor(

    /**
     * Identificador único do autor.
     *
     * Esse ID permite relacionar a atividade ao usuário que a criou.
     */
    val id: String,

    /**
     * Nome exibido do autor.
     */
    val name: String,

    /**
     * Iniciais usadas no avatar quando o autor não possui imagem de perfil.
     */
    val avatarInitials: String,

    /**
     * URL da imagem de avatar do autor.
     *
     * Quando estiver vazia, a interface deve exibir o avatar gerado com as
     * iniciais do usuário.
     */
    val avatarUrl: String = ""
)