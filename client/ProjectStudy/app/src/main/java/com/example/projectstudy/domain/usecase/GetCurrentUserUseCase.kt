package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.UserRepository
import com.example.projectstudy.domain.model.User
import javax.inject.Inject

/**
 * Caso de uso responsável por buscar o usuário atual do app.
 *
 * Essa classe centraliza a ação de obter o usuário ativo, evitando que ViewModels
 * acessem diretamente o [UserRepository].
 *
 * O usuário atual pode ser usado em fluxos como:
 * - exibição do perfil;
 * - criação de sessão manual;
 * - identificação do autor de uma atividade;
 * - destaque do usuário no ranking.
 */
class GetCurrentUserUseCase @Inject constructor(

    /**
     * Repositório responsável por fornecer os dados do usuário atual.
     */
    private val repository: UserRepository
) {

    /**
     * Executa a busca pelo usuário atual.
     *
     * @return Usuário atual convertido para o modelo de domínio.
     */
    suspend operator fun invoke(): User {
        return repository.getCurrentUser()
    }
}