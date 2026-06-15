package com.example.projectstudy.di

import android.content.Context
import androidx.room.Room
import com.example.projectstudy.data.local.ProjectStudyDatabase
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideProjectStudyDatabase(
        @ApplicationContext context: Context
    ): ProjectStudyDatabase {
        return Room.databaseBuilder(
            context,
            ProjectStudyDatabase::class.java,
            "project_study.db"
        ).build()
    }

    @Provides
    fun provideUserDao(
        database: ProjectStudyDatabase
    ): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideGroupDao(
        database: ProjectStudyDatabase
    ): GroupDao {
        return database.groupDao()
    }

    @Provides
    fun provideStudyActivityDao(
        database: ProjectStudyDatabase
    ): StudyActivityDao {
        return database.studyActivityDao()
    }

    @Provides
    fun provideRankingDao(
        database: ProjectStudyDatabase
    ): RankingDao {
        return database.rankingDao()
    }
}