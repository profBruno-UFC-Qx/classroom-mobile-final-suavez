package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade local que representa uma atividade ou sessão de estudo publicada no app.
 *
 * Essa tabela armazena os dados principais de uma sessão, como autor, título,
 * disciplina, descrição, duração, mídia principal e datas relacionadas.
 *
 * Ela é usada em telas como:
 * - feed do grupo;
 * - perfil do usuário;
 * - histórico de atividades;
 * - criação manual de sessão.
 *
 * Como o app segue uma abordagem offline-first, a atividade pode ser salva
 * localmente antes de ser sincronizada com uma API remota.
 */
@Entity(tableName = "study_activities")
data class StudyActivityEntity(

    /**
     * Identificador único da atividade.
     *
     * Para sessões criadas localmente, normalmente é gerado com UUID.
     */
    @PrimaryKey
    val id: String,

    /**
     * Identificador do autor da atividade.
     */
    val authorId: String,

    /**
     * Nome exibido do autor da atividade.
     */
    val authorName: String,

    /**
     * Iniciais usadas no avatar do autor quando não houver imagem.
     */
    val authorAvatarInitials: String,

    /**
     * URL da imagem de avatar do autor.
     *
     * Quando estiver vazia, a interface deve exibir o avatar gerado pelas iniciais.
     */
    val authorAvatarUrl: String,

    /**
     * Título da sessão ou atividade de estudo.
     */
    val title: String,

    /**
     * Disciplina, assunto ou categoria estudada.
     */
    val subject: String,

    /**
     * Descrição opcional da sessão.
     *
     * Pode conter observações, conteúdo estudado ou anotações feitas pelo usuário.
     */
    val description: String,

    /**
     * Duração da sessão em minutos.
     *
     * Esse valor é usado para cálculos de progresso, ranking e estatísticas.
     */
    val durationMinutes: Int,

    /**
     * Duração da sessão em segundos.
     *
     * Mantém maior precisão para casos em que a sessão foi registrada com timer
     * ou seletor de duração mais detalhado.
     */
    val durationSeconds: Int,

    /**
     * URI ou URL da imagem principal da atividade.
     *
     * Normalmente recebe a primeira mídia selecionada pelo usuário.
     */
    val imageUrl: String,

    /**
     * Quantidade de reações recebidas na atividade.
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
     * Esse campo permite diferenciar sessões registradas manualmente de sessões
     * criadas por timer ou por outro fluxo automático.
     */
    val isManual: Boolean,

    /**
     * Indica se a atividade já foi sincronizada com uma API remota.
     *
     * Enquanto esse valor for false, a atividade existe apenas localmente e pode
     * precisar ser enviada ao servidor futuramente.
     */
    val isSynced: Boolean,

    /**
     * Ação pendente de sincronização.
     *
     * Exemplos possíveis:
     * - "CREATE": atividade criada localmente e ainda não enviada para a API;
     * - "UPDATE": atividade alterada localmente e ainda não sincronizada;
     * - "DELETE": atividade marcada localmente para remoção remota.
     *
     * Quando não houver sincronização pendente, esse valor pode ser null.
     */
    val pendingSyncAction: String?
)