package com.example.projectstudy.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity
import com.example.projectstudy.data.local.entity.GroupEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity

/**
 * Representa uma atividade de estudo carregada junto com suas relações.
 *
 * Essa classe é usada pelo Room para buscar uma [StudyActivityEntity] acompanhada
 * dos dados relacionados a ela, evitando que a camada de repositório precise
 * fazer consultas separadas manualmente.
 *
 * Relações carregadas:
 * - atividade principal;
 * - grupos aos quais a atividade foi publicada;
 * - mídias anexadas à atividade.
 *
 * Esse modelo é especialmente útil para converter os dados locais para o modelo
 * de domínio [StudyActivity], que precisa de informações como `groupIds` e
 * `mediaUris`.
 */
data class StudyActivityWithRelations(

    /**
     * Entidade principal da atividade de estudo.
     *
     * O [Embedded] indica que os campos de [StudyActivityEntity] fazem parte
     * diretamente do resultado principal da consulta.
     */
    @Embedded
    val activity: StudyActivityEntity,

    /**
     * Grupos associados à atividade.
     *
     * Uma atividade pode estar vinculada a vários grupos, e um grupo pode possuir
     * várias atividades. Por isso, essa relação é muitos-para-muitos.
     *
     * A tabela [ActivityGroupCrossRef] funciona como tabela intermediária entre:
     * - `activityId`, que referencia a atividade;
     * - `groupId`, que referencia o grupo.
     */
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ActivityGroupCrossRef::class,
            parentColumn = "activityId",
            entityColumn = "groupId"
        )
    )
    val groups: List<GroupEntity>,

    /**
     * Mídias anexadas à atividade.
     *
     * Essa é uma relação um-para-muitos, em que uma atividade pode possuir várias
     * mídias. Cada [ActivityMediaEntity] armazena o `activityId` da atividade à
     * qual pertence.
     */
    @Relation(
        parentColumn = "id",
        entityColumn = "activityId"
    )
    val media: List<ActivityMediaEntity>
)