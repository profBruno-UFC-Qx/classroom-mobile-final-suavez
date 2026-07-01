package com.example.projectstudy.domain.model

/**
 * Modelo de domínio que representa uma atividade ou sessão de estudo.
 *
 * Esse modelo é usado pelas camadas de domínio e interface para exibir uma
 * sessão publicada no app, sem depender diretamente da entidade local do Room
 * ou de uma futura resposta da API.
 *
 * Uma atividade pode estar associada a um ou mais grupos, possuir um autor,
 * conter informações sobre o conteúdo estudado, duração, mídias anexadas,
 * reações e datas relacionadas à sessão.
 */
data class StudyActivity(

    /**
     * Identificador único da atividade.
     */
    val id: String,

    /**
     * Lista de identificadores dos grupos aos quais a atividade pertence.
     *
     * Uma mesma sessão pode ser publicada em mais de um grupo de estudo.
     */
    val groupIds: List<String>,

    /**
     * Autor responsável pela criação da atividade.
     *
     * Contém apenas os dados necessários para exibir o autor na interface.
     */
    val author: ActivityAuthor,

    /**
     * Título da atividade ou sessão de estudo.
     *
     * Geralmente resume o que foi estudado.
     */
    val title: String,

    /**
     * Disciplina, assunto ou categoria estudada.
     */
    val subject: String,

    /**
     * Descrição da atividade.
     *
     * Pode conter observações, tópicos estudados ou anotações feitas pelo usuário.
     */
    val description: String,

    /**
     * Duração total da sessão em minutos.
     *
     * Esse valor é usado para atualizar progresso, estatísticas e ranking.
     */
    val durationMinutes: Int,

    /**
     * URL ou URI da imagem principal da atividade.
     *
     * Normalmente representa a primeira mídia anexada à sessão.
     */
    val imageUrl: String,

    /**
     * Lista de URIs das mídias anexadas à atividade.
     *
     * Quando não houver anexos, a lista permanece vazia.
     */
    val mediaUris: List<String> = emptyList(),

    /**
     * Quantidade de reações recebidas pela atividade.
     */
    val reactions: Int,

    /**
     * Momento de início da sessão em milissegundos.
     */
    val startedAtMillis: Long,

    /**
     * Momento de término da sessão em milissegundos.
     */
    val endedAtMillis: Long,

    /**
     * Momento em que a atividade foi criada no app em milissegundos.
     */
    val createdAtMillis: Long,

    /**
     * Indica se a atividade foi criada manualmente pelo usuário.
     *
     * Quando falso, a atividade pode ter sido criada por outro fluxo, como timer
     * ou sincronização futura.
     */
    val isManual: Boolean = false
)