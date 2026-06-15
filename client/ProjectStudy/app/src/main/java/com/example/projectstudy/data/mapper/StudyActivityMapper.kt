package com.example.projectstudy.data.mapper

import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity
import com.example.projectstudy.data.local.relation.StudyActivityWithRelations
import com.example.projectstudy.domain.model.ActivityAuthor
import com.example.projectstudy.domain.model.StudyActivity

fun StudyActivityWithRelations.toDomain(): StudyActivity {
    return StudyActivity(
        id = activity.id,
        groupIds = groups.map { group ->
            group.id
        },
        author = ActivityAuthor(
            id = activity.authorId,
            name = activity.authorName,
            avatarInitials = activity.authorAvatarInitials,
            avatarUrl = activity.authorAvatarUrl
        ),
        title = activity.title,
        subject = activity.subject,
        description = activity.description,
        durationMinutes = activity.durationMinutes,
        imageUrl = activity.imageUrl,
        mediaUris = media
            .sortedBy { item -> item.position }
            .map { item -> item.uri },
        reactions = activity.reactions,
        startedAtMillis = activity.startedAtMillis,
        endedAtMillis = activity.endedAtMillis,
        createdAtMillis = activity.createdAtMillis,
        isManual = activity.isManual
    )
}

fun StudyActivity.toEntity(
    isSynced: Boolean = true,
    pendingSyncAction: String? = null
): StudyActivityEntity {
    return StudyActivityEntity(
        id = id,

        authorId = author.id,
        authorName = author.name,
        authorAvatarInitials = author.avatarInitials,
        authorAvatarUrl = author.avatarUrl,

        title = title,
        subject = subject,
        description = description,

        durationMinutes = durationMinutes,
        durationSeconds = durationMinutes * 60,

        imageUrl = imageUrl,

        reactions = reactions,

        startedAtMillis = startedAtMillis,
        endedAtMillis = endedAtMillis,
        createdAtMillis = createdAtMillis,

        isManual = isManual,
        isSynced = isSynced,
        pendingSyncAction = pendingSyncAction
    )
}

fun StudyActivity.toGroupRefs(): List<ActivityGroupCrossRef> {
    return groupIds.map { groupId ->
        ActivityGroupCrossRef(
            activityId = id,
            groupId = groupId
        )
    }
}

fun StudyActivity.toMediaEntities(): List<ActivityMediaEntity> {
    return mediaUris.mapIndexed { index, uri ->
        ActivityMediaEntity(
            id = "${id}_media_$index",
            activityId = id,
            uri = uri,
            position = index
        )
    }
}