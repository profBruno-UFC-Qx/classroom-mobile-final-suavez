package com.example.projectstudy.di

import com.example.projectstudy.data.local.repository.LocalActivityRepository
import com.example.projectstudy.data.local.repository.LocalAuthRepository
import com.example.projectstudy.data.local.repository.LocalGroupRepository
import com.example.projectstudy.data.local.repository.LocalRankingRepository
import com.example.projectstudy.data.local.repository.LocalSessionRepository
import com.example.projectstudy.data.local.repository.LocalUserRepository
import com.example.projectstudy.data.repository.ActivityRepository
import com.example.projectstudy.data.repository.AuthRepository
import com.example.projectstudy.data.repository.GroupRepository
import com.example.projectstudy.data.repository.RankingRepository
import com.example.projectstudy.data.repository.SessionRepository
import com.example.projectstudy.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo responsável por vincular interfaces de repositório às suas implementações.
 *
 * Este módulo usa [Binds] porque cada função apenas informa ao Hilt qual classe
 * concreta deve ser usada quando alguma dependência solicitar uma interface.
 *
 * Com isso, ViewModels e casos de uso dependem de contratos, como
 * [ActivityRepository] e [UserRepository], sem conhecer diretamente as classes
 * locais que acessam Room, SharedPreferences ou outras fontes de dados.
 *
 * Esse desacoplamento facilita manutenção, testes e futuras trocas de
 * implementação, por exemplo substituindo repositórios locais por versões que
 * também sincronizam com uma API remota.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Vincula o contrato de autenticação à implementação local.
     *
     * A implementação atual salva o estado de login localmente, permitindo que
     * o app controle se deve iniciar na tela de login ou na tela principal.
     *
     * @param repository Implementação local do repositório de autenticação.
     * @return Contrato de autenticação usado pelo app.
     */
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        repository: LocalAuthRepository
    ): AuthRepository

    /**
     * Vincula o contrato de atividades à implementação local.
     *
     * Essa implementação observa atividades salvas no banco Room e alimenta
     * telas como feed e perfil.
     *
     * @param repository Implementação local do repositório de atividades.
     * @return Contrato de atividades usado pela camada de domínio.
     */
    @Binds
    @Singleton
    abstract fun bindActivityRepository(
        repository: LocalActivityRepository
    ): ActivityRepository

    /**
     * Vincula o contrato de grupos à implementação local.
     *
     * Essa implementação fornece os grupos salvos no banco local, incluindo o
     * grupo principal usado no feed, ranking e criação de sessões.
     *
     * @param repository Implementação local do repositório de grupos.
     * @return Contrato de grupos usado pela camada de domínio.
     */
    @Binds
    @Singleton
    abstract fun bindGroupRepository(
        repository: LocalGroupRepository
    ): GroupRepository

    /**
     * Vincula o contrato de ranking à implementação local.
     *
     * Essa implementação observa as entradas de ranking salvas localmente e
     * permite que a interface reaja a alterações após novas sessões de estudo.
     *
     * @param repository Implementação local do repositório de ranking.
     * @return Contrato de ranking usado pela camada de domínio.
     */
    @Binds
    @Singleton
    abstract fun bindRankingRepository(
        repository: LocalRankingRepository
    ): RankingRepository

    /**
     * Vincula o contrato de usuário à implementação local.
     *
     * Essa implementação busca o usuário atual salvo no banco local, usado em
     * telas como perfil e em fluxos como criação manual de sessão.
     *
     * @param repository Implementação local do repositório de usuário.
     * @return Contrato de usuário usado pela camada de domínio.
     */
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        repository: LocalUserRepository
    ): UserRepository

    /**
     * Vincula o contrato de sessões à implementação local.
     *
     * Essa implementação cria sessões manuais no banco local, registra mídias,
     * associa a atividade aos grupos e atualiza progresso e ranking.
     *
     * @param repository Implementação local do repositório de sessões.
     * @return Contrato de sessões usado pela camada de domínio.
     */
    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        repository: LocalSessionRepository
    ): SessionRepository
}