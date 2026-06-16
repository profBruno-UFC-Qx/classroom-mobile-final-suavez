package com.example.projectstudy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity
import com.example.projectstudy.data.local.relation.StudyActivityWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyActivityDao {
    @Transaction
    @Query(
        """
        SELECT study_activities.*
        FROM study_activities
        INNER JOIN activity_group_cross_refs
            ON study_activities.id = activity_group_cross_refs.activityId
        WHERE activity_group_cross_refs.groupId = :groupId
        ORDER BY study_activities.startedAtMillis DESC
        """
    )
    fun observeActivitiesByGroupId(
        groupId: String
    ): Flow<List<StudyActivityWithRelations>>

    @Transaction
    @Query(
        """
        SELECT study_activities.*
        FROM study_activities
        WHERE authorId = :userId
        ORDER BY startedAtMillis DESC
        """
    )
    fun observeActivitiesByUserId(
        userId: String
    ): Flow<List<StudyActivityWithRelations>>

    @Query(
        """
        SELECT *
        FROM study_activities
        WHERE isSynced = 0
        ORDER BY createdAtMillis ASC
        """
    )
    suspend fun getPendingSyncActivities(): List<StudyActivityEntity>

    @Upsert
    suspend fun upsertActivity(
        activity: StudyActivityEntity
    )

    @Upsert
    suspend fun upsertActivities(
        activities: List<StudyActivityEntity>
    )

    @Upsert
    suspend fun upsertActivityGroupRefs(
        refs: List<ActivityGroupCrossRef>
    )

    @Upsert
    suspend fun upsertMedia(
        media: List<ActivityMediaEntity>
    )

    @Query("DELETE FROM activity_group_cross_refs WHERE activityId = :activityId")
    suspend fun deleteGroupRefsByActivityId(
        activityId: String
    )

    @Query("DELETE FROM activity_media WHERE activityId = :activityId")
    suspend fun deleteMediaByActivityId(
        activityId: String
    )
}