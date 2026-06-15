package com.example.projectstudy.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity
import com.example.projectstudy.data.local.entity.GroupEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity

data class StudyActivityWithRelations(
    @Embedded
    val activity: StudyActivityEntity,

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

    @Relation(
        parentColumn = "id",
        entityColumn = "activityId"
    )
    val media: List<ActivityMediaEntity>
)