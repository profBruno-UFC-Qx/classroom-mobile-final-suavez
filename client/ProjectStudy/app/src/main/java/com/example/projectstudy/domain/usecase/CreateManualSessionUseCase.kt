package com.example.projectstudy.domain.usecase

import com.example.projectstudy.data.repository.SessionRepository
import com.example.projectstudy.domain.model.CreateManualSessionData
import javax.inject.Inject

/**
 * Caso de uso responsável por criar uma sessão manual de estudo.
 *
 * Essa classe representa a ação de negócio executada quando o usuário preenche
 * os dados de uma sessão manual e confirma o registro.
 *
 * O caso de uso delega a persistência para [SessionRepository], mantendo a
 * ViewModel desacoplada da implementação concreta do repositório e da camada
 * de dados.
 */
class CreateManualSessionUseCase @Inject constructor(

    /**
     * Repositório responsável por salvar a sessão manual.
     */
    private val repository: SessionRepository
) {

    /**
     * Executa a criação de uma sessão manual.
     *
     * Os dados recebidos em [data] são enviados ao repositório, que fica
     * responsável por salvar a atividade no banco local, associá-la aos grupos,
     * registrar mídias anexadas e atualizar progresso/ranking quando necessário.
     *
     * @param data Dados preenchidos pelo usuário para criação da sessão manual.
     */
    suspend operator fun invoke(
        data: CreateManualSessionData
    ) {
        repository.createManualSession(data)
    }
}