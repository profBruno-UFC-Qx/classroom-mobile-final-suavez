package com.example.projectstudy.data.repository

import kotlinx.coroutines.flow.StateFlow

/**
 * Contrato responsável por controlar o estado de autenticação do usuário.
 *
 * Essa interface abstrai a origem dos dados da sessão. Atualmente, a implementação
 * local pode salvar o estado em SharedPreferences, mas futuramente esse contrato
 * também pode ser adaptado para trabalhar com token, API, DataStore ou outro
 * mecanismo de autenticação.
 *
 * O principal objetivo é permitir que o app saiba se o usuário continua logado
 * mesmo após recriações da Activity, como ao trocar o tema claro/escuro do sistema
 * ou fechar e abrir o aplicativo novamente.
 */
interface AuthRepository {

    /**
     * Estado observável que indica se existe uma sessão ativa.
     *
     * Como é um StateFlow, qualquer tela ou ViewModel que observar esse valor será
     * atualizado automaticamente quando o usuário fizer login ou logout.
     */
    val isLoggedIn: StateFlow<Boolean>

    /**
     * Atualiza o estado de login do usuário.
     *
     * Deve ser chamado quando o login ou cadastro for concluído com sucesso,
     * salvando localmente que o usuário está autenticado.
     *
     * @param value true para marcar o usuário como logado, false para remover a sessão.
     */
    suspend fun setLoggedIn(value: Boolean)

    /**
     * Encerra a sessão atual do usuário.
     *
     * Deve limpar o estado local de autenticação para que o app redirecione
     * o usuário novamente para a tela de login.
     */
    suspend fun logout()
}