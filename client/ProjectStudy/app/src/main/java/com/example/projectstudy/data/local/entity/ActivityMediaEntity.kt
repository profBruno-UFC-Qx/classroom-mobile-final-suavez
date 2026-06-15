package com.example.projectstudy.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
    @PrimaryKey
    val id: String,
    val activityId: String,
    val uri: String,
    val position: Int
)