package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.CreateManualSessionData

/**
 * Contrato responsável por registrar sessões manuais de estudo.
 *
 * Esse repositório abstrai o processo de criação de uma sessão manual,
 * permitindo que a camada de domínio ou ViewModel solicite o salvamento da
 * sessão sem depender diretamente do Room, de DAOs ou de regras específicas
 * de persistência.
 *
 * Em uma arquitetura offline-first, a implementação pode salvar a sessão
 * primeiro no banco local e marcar a atividade como pendente de sincronização
 * com uma API remota futuramente.
 */
interface SessionRepository {

    /**
     * Cria uma nova sessão manual de estudo.
     *
     * A implementação desse método deve transformar os dados informados pelo
     * usuário em uma atividade de estudo, salvar seus vínculos com grupos,
     * registrar mídias anexadas quando existirem e atualizar informações
     * relacionadas, como progresso do grupo e ranking local.
     *
     * @param data Dados preenchidos pelo usuário para criação da sessão manual.
     */
    suspend fun createManualSession(
        data: CreateManualSessionData
    )
}