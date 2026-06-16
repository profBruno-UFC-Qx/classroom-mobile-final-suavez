package com.example.projectstudy.data.repository

import com.example.projectstudy.domain.model.Group
import kotlinx.coroutines.flow.Flow

/**
 * Contrato responsável por fornecer grupos de estudo para a camada de domínio.
 *
 * O repositório abstrai a origem dos dados, permitindo que ViewModels e casos
 * de uso acessem os grupos sem depender diretamente do Room, de uma API remota
 * ou de qualquer implementação específica.
 *
 * Como o app segue uma abordagem offline-first, as implementações podem observar
 * os dados salvos localmente e, futuramente, sincronizá-los com uma fonte remota.
 */
interface GroupRepository {

    /**
     * Observa o primeiro grupo associado ao usuário atual.
     *
     * Atualmente, o app utiliza esse grupo como grupo principal para alimentar
     * telas como feed, ranking e criação manual de sessões.
     *
     * Como retorna [Flow], qualquer alteração no grupo local será emitida
     * automaticamente para quem estiver observando esse fluxo.
     *
     * @return Fluxo com o grupo principal do usuário.
     */
    fun observeFirstUserGroup(): Flow<Group>

    /**
     * Observa todos os grupos associados ao usuário atual.
     *
     * Esse método permite que a interface acompanhe mudanças na lista de grupos,
     * como criação, atualização, entrada em novos grupos ou sincronizações futuras.
     *
     * @return Fluxo com a lista de grupos do usuário.
     */
    fun observeUserGroups(): Flow<List<Group>>
}