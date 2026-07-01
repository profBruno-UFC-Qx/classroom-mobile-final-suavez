package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidade que representa uma mídia anexada a uma atividade de estudo.
 *
 * Essa tabela permite que uma atividade possua várias mídias, como imagens
 * selecionadas pelo usuário durante o registro de uma sessão manual.
 *
 * A relação com [StudyActivityEntity] é do tipo um-para-muitos:
 * - uma atividade pode ter várias mídias;
 * - cada mídia pertence a uma única atividade.
 *
 * A chave estrangeira usa `onDelete = ForeignKey.CASCADE`, então, quando uma
 * atividade for removida, todas as mídias associadas a ela também serão removidas
 * automaticamente do banco local.
 */
@Entity(
    tableName = "activity_media",
    foreignKeys = [
        ForeignKey(
            entity = StudyActivityEntity::class,
            parentColumns = ["id"],
            childColumns = ["activityId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["activityId"])
    ]
)
data class ActivityMediaEntity(

    /**
     * Identificador único da mídia.
     *
     * Pode ser gerado a partir do ID da atividade e da posição da mídia na lista,
     * garantindo que cada anexo tenha uma chave própria no banco local.
     */
    @PrimaryKey
    val id: String,

    /**
     * Identificador da atividade à qual esta mídia pertence.
     *
     * Referencia o campo `id` da tabela [StudyActivityEntity].
     */
    val activityId: String,

    /**
     * URI local da mídia.
     *
     * Esse valor representa o caminho ou identificador usado pelo Android para
     * acessar a imagem selecionada pelo usuário.
     */
    val uri: String,

    /**
     * Posição da mídia dentro da lista de anexos da atividade.
     *
     * Esse campo permite preservar a ordem original em que as mídias foram
     * selecionadas ou exibidas na interface.
     */
    val position: Int
)