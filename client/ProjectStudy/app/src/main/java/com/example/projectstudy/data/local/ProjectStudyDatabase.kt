package com.example.projectstudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.local.dao.UserDao
import com.example.projectstudy.data.local.entity.GroupEntity
import com.example.projectstudy.data.local.entity.RankingEntryEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity
import com.example.projectstudy.data.local.entity.UserEntity
import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity

@Database(
    entities = [
        UserEntity::class,
        GroupEntity::class,
        StudyActivityEntity::class,
        ActivityGroupCrossRef::class,
        ActivityMediaEntity::class,
        RankingEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ProjectStudyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun groupDao(): GroupDao
    abstract fun studyActivityDao(): StudyActivityDao
    abstract fun rankingDao(): RankingDao
}