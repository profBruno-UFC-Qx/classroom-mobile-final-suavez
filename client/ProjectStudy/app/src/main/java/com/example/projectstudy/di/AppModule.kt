package com.example.projectstudy.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Módulo principal de injeção de dependências da aplicação.
 *
 * Esse módulo é instalado no [SingletonComponent], ou seja, as dependências
 * declaradas aqui ficam disponíveis durante todo o ciclo de vida do app.
 *
 * Atualmente, este módulo está vazio, mas ele pode ser usado futuramente para
 * fornecer dependências globais que não precisam de bindings abstratos, como:
 * - instâncias configuradas manualmente;
 * - objetos utilitários;
 * - clientes de rede;
 * - configurações globais da aplicação.
 *
 * Para interfaces e implementações, normalmente é melhor usar um módulo abstrato
 * com `@Binds`, como no caso dos repositórios.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule