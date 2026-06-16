package com.example.projectstudy.data.local.repository

import android.content.Context
import com.example.projectstudy.data.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Implementação local do controle de autenticação do usuário.
 *
 * Essa classe salva o estado da sessão em SharedPreferences para que o app
 * consiga manter o usuário logado mesmo quando a Activity for recriada,
 * como ao trocar o tema claro/escuro do sistema ou ao reabrir o aplicativo.
 *
 * Também expõe esse estado como StateFlow, permitindo que ViewModels e telas
 * reajam automaticamente quando o usuário fizer login ou logout.
 */
@Singleton
class LocalAuthRepository @Inject constructor(
    @ApplicationContext context: Context
) : AuthRepository {

    /**
     * SharedPreferences usado para persistir informações simples da sessão.
     *
     * Neste momento, salva apenas se o usuário está logado ou não.
     * Futuramente pode ser substituído por DataStore ou por uma estrutura
     * mais completa com token de acesso, refresh token e dados do usuário.
     */
    private val preferences = context.getSharedPreferences(
        "auth_session",
        Context.MODE_PRIVATE
    )

    /**
     * Estado interno de autenticação.
     *
     * O valor inicial é carregado diretamente do SharedPreferences. Assim,
     * quando o app abre novamente, o estado anterior da sessão é restaurado.
     */
    private val _isLoggedIn = MutableStateFlow(
        preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    )

    /**
     * Estado público e observável da sessão.
     *
     * As telas e ViewModels devem observar este StateFlow para saber se o
     * usuário está autenticado, sem acessar diretamente o SharedPreferences.
     */
    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    /**
     * Atualiza o estado de login do usuário.
     *
     * O valor é salvo no SharedPreferences e também publicado no StateFlow,
     * garantindo que a navegação seja atualizada imediatamente.
     *
     * @param value true quando o usuário está logado, false quando não há sessão ativa.
     */
    override suspend fun setLoggedIn(value: Boolean) {
        preferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, value)
            .apply()

        _isLoggedIn.value = value
    }

    /**
     * Encerra a sessão atual do usuário.
     *
     * Remove o estado de login localmente e atualiza o StateFlow para que o app
     * possa redirecionar o usuário para a tela de login.
     */
    override suspend fun logout() {
        preferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .apply()

        _isLoggedIn.value = false
    }

    /**
     * Chaves usadas para salvar os dados da sessão local.
     */
    private companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
    }
}