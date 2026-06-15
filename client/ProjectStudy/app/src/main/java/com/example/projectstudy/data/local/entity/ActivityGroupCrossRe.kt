package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

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
    val activityId: String,
    val groupId: String
)