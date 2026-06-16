package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.projectstudy.domain.model.RankingMetric

/**
 * Entidade local que representa um grupo de estudos.
 *
 * Essa tabela armazena as informações principais de cada grupo usado no app,
 * incluindo dados visuais, progresso coletivo, progresso individual do usuário
 * e regras de ranking.
 *
 * Os grupos são usados como base para telas como:
 * - feed de atividades;
 * - ranking;
 * - seleção de grupo na criação manual de sessão;
 * - acompanhamento de meta de estudo.
 */
@Entity(tableName = "groups")
data class GroupEntity(

    /**
     * Identificador único do grupo.
     *
     * Esse ID é usado para relacionar o grupo com atividades, ranking e outras
     * estruturas do banco local.
     */
    @PrimaryKey
    val id: String,

    /**
     * Nome exibido para o grupo.
     */
    val name: String,

    /**
     * Descrição curta do grupo.
     *
     * Pode explicar o objetivo do grupo, disciplina, turma ou contexto de estudo.
     */
    val description: String,

    /**
     * URL ou caminho da imagem/banner do grupo.
     *
     * Usado para compor a apresentação visual do grupo na interface.
     */
    val bannerUrl: String,

    /**
     * Quantidade de membros participantes do grupo.
     */
    val memberCount: Int,

    /**
     * Código usado para convite ou entrada no grupo.
     */
    val inviteCode: String,

    /**
     * Meta de estudo do grupo em minutos.
     *
     * Representa o objetivo coletivo que o grupo pretende alcançar.
     */
    val goalMinutes: Int,

    /**
     * Progresso atual do grupo em minutos.
     *
     * Esse valor é incrementado quando novas sessões de estudo são publicadas
     * em atividades associadas ao grupo.
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
     * Métrica usada para ordenar o ranking do grupo.
     *
     * É salva como String no banco para facilitar a persistência do enum de domínio.
     * Na camada de mapper, esse valor é convertido para [RankingMetric].
     */
    val rankingMetric: String,

    /**
     * Data de criação do grupo em milissegundos.
     */
    val createdAtMillis: Long,

    /**
     * Momento da última sincronização ou atualização local relevante.
     *
     * Esse campo ajuda a controlar dados offline-first e futuras sincronizações
     * com uma API remota.
     */
    val lastSyncedAtMillis: Long
)