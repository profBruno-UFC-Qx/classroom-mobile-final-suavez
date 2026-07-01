package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.User

/**
 * Contrato responsável por fornecer dados do usuário atual para a camada de domínio.
 *
 * O repositório abstrai a origem dos dados do usuário, permitindo que casos de
 * uso e ViewModels acessem o usuário atual sem depender diretamente do Room,
 * de uma API remota ou de qualquer implementação específica.
 *
 * Atualmente, a implementação pode buscar o usuário salvo localmente. Futuramente,
 * esse contrato pode ser mantido mesmo que os dados passem a vir de autenticação
 * real ou sincronização com uma API.
 */
interface UserRepository {

    /**
     * Busca o usuário atual do app.
     *
     * Esse método é usado quando alguma regra de negócio precisa saber quem é
     * o usuário ativo, por exemplo ao criar uma sessão manual, exibir o perfil
     * ou montar dados relacionados ao autor de uma atividade.
     *
     * @return Usuário atual convertido para o modelo de domínio.
     */
    suspend fun getCurrentUser(): User
}