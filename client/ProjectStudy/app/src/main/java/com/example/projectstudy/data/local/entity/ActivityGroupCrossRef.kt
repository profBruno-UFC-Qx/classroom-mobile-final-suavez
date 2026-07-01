package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Tabela intermediária que relaciona atividades de estudo e grupos.
 *
 * Essa entidade representa uma relação muitos-para-muitos:
 * - uma atividade pode ser publicada em vários grupos;
 * - um grupo pode conter várias atividades.
 *
 * Por isso, a tabela possui uma chave primária composta por `activityId` e
 * `groupId`, impedindo que a mesma atividade seja associada ao mesmo grupo
 * mais de uma vez.
 *
 * As chaves estrangeiras usam `onDelete = ForeignKey.CASCADE`, então:
 * - se uma atividade for removida, seus vínculos com grupos também são removidos;
 * - se um grupo for removido, seus vínculos com atividades também são removidos.
 */
@Entity(
    tableName = "activity_group_cross_refs",
    primaryKeys = [
        "activityId",
        "groupId"
    ],
    foreignKeys = [
        ForeignKey(
            entity = StudyActivityEntity::class,
            parentColumns = ["id"],
            childColumns = ["activityId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["activityId"]),
        Index(value = ["groupId"])
    ]
)
data class ActivityGroupCrossRef(

    /**
     * Identificador da atividade de estudo relacionada.
     *
     * Referencia o campo `id` da tabela de atividades.
     */
    val activityId: String,

    /**
     * Identificador do grupo relacionado.
     *
     * Referencia o campo `id` da tabela de grupos.
     */
    val groupId: String
)