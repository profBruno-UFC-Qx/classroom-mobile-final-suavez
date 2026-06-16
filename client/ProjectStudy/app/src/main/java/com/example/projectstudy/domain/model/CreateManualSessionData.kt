package com.example.projectstudy.domain.model

/**
 * Modelo de domínio que representa os dados necessários para criar uma sessão manual.
 *
 * Esse modelo agrupa as informações preenchidas pelo usuário na tela de registro
 * manual de sessão, como título, disciplina, descrição, data, horário, duração,
 * mídias anexadas e grupos onde a atividade será publicada.
 *
 * Ele é enviado para o [com.example.projectstudy.data.repository.SessionRepository],
 * que fica responsável por transformar esses dados em uma atividade de estudo
 * salva no banco local.
 */
data class CreateManualSessionData(

    /**
     * Título da sessão manual.
     *
     * Geralmente resume o que foi estudado ou o objetivo da sessão.
     */
    val title: String,

    /**
     * Disciplina, assunto ou categoria estudada.
     */
    val subject: String,

    /**
     * Descrição opcional da sessão.
     *
     * Pode conter observações, tópicos estudados ou anotações feitas pelo usuário.
     */
    val description: String,

    /**
     * Data da sessão em milissegundos.
     *
     * Esse valor representa o dia selecionado pelo usuário para a sessão manual.
     */
    val dateMillis: Long,

    /**
     * Horário de início da sessão em minutos desde o início do dia.
     *
     * Exemplo:
     * - 08:30 equivale a 510 minutos;
     * - 14:00 equivale a 840 minutos.
     */
    val startTimeMinutes: Int,

    /**
     * Duração total da sessão em minutos.
     *
     * Esse valor é usado para calcular o horário de término, atualizar progresso
     * do grupo e recalcular o ranking local.
     */
    val durationMinutes: Int,

    /**
     * Lista de URIs das mídias anexadas à sessão.
     *
     * Cada item representa uma imagem ou arquivo selecionado pelo usuário.
     */
    val mediaUris: List<String>,

    /**
     * Lista de grupos onde a sessão será publicada.
     *
     * Uma mesma sessão pode ser associada a um ou mais grupos de estudo.
     */
    val groupIds: List<String>
)