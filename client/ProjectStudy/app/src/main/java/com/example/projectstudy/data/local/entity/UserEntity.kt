package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade local que representa um usuário do app.
 *
 * Essa tabela armazena os dados básicos do usuário, informações acadêmicas,
 * dados de avatar e estatísticas gerais de estudo.
 *
 * Os dados desta entidade são usados em telas como:
 * - perfil do usuário;
 * - feed de atividades;
 * - ranking;
 * - identificação do autor de uma sessão de estudo.
 *
 * Atualmente, o app utiliza dados locais para simular o usuário autenticado.
 * Futuramente, essa entidade pode ser sincronizada com uma API remota.
 */
@Entity(tableName = "users")
data class UserEntity(

    /**
     * Identificador único do usuário.
     *
     * Esse ID é usado para relacionar o usuário com atividades, ranking e
     * demais dados associados à conta.
     */
    @PrimaryKey
    val id: String,

    /**
     * Nome completo ou nome de exibição do usuário.
     */
    val name: String,

    /**
     * Nome de usuário usado como identificador curto no app.
     *
     * É exibido em locais como ranking, perfil e cards de atividade.
     */
    val username: String,

    /**
     * Email associado ao usuário.
     */
    val email: String,

    /**
     * Instituição de ensino do usuário.
     */
    val institution: String,

    /**
     * Curso do usuário.
     */
    val course: String,

    /**
     * Iniciais usadas no avatar quando não houver imagem de perfil.
     */
    val avatarInitials: String,

    /**
     * URL da imagem de avatar do usuário.
     *
     * Quando estiver vazia, a interface deve exibir o avatar gerado com as
     * iniciais e uma cor baseada no usuário.
     */
    val avatarUrl: String,

    /**
     * Total de minutos estudados pelo usuário.
     *
     * Esse valor pode ser usado para estatísticas gerais do perfil.
     */
    val totalMinutes: Int,

    /**
     * Quantidade total de atividades ou sessões registradas pelo usuário.
     */
    val totalActivities: Int,

    /**
     * Sequência atual de dias de estudo do usuário.
     *
     * Representa quantos dias consecutivos o usuário manteve atividade.
     */
    val streakDays: Int,

    /**
     * Momento da última sincronização ou atualização local relevante.
     *
     * Esse campo ajuda a controlar futuras sincronizações com uma API remota
     * em uma arquitetura offline-first.
     */
    val lastSyncedAtMillis: Long
)