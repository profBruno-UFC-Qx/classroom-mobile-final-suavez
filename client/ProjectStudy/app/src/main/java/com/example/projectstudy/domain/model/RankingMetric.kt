package com.example.projectstudy.domain.model

/**
 * Enum que define a métrica usada para calcular ou ordenar o ranking de um grupo.
 *
 * Cada grupo pode ter uma regra principal de classificação, como tempo total
 * estudado ou quantidade de dias ativos.
 *
 * Esse enum evita o uso de Strings soltas na camada de domínio, tornando o código
 * mais seguro e mais fácil de manter.
 */
enum class RankingMetric {

    /**
     * Ranking baseado no tempo total estudado.
     *
     * Usuários com mais minutos acumulados tendem a ocupar posições mais altas.
     */
    TIME,

    /**
     * Ranking baseado na quantidade de dias ativos.
     *
     * Usuários com maior frequência de estudo podem ocupar posições mais altas.
     */
    DAYS
}