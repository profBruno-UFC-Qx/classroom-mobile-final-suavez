package com.example.projectstudy.di

import com.example.projectstudy.data.local.repository.LocalActivityRepository
import com.example.projectstudy.data.local.repository.LocalGroupRepository
import com.example.projectstudy.data.local.repository.LocalRankingRepository
import com.example.projectstudy.data.local.repository.LocalSessionRepository
import com.example.projectstudy.data.local.repository.LocalUserRepository
import com.example.projectstudy.data.mock.FakeActivityRepository
import com.example.projectstudy.data.mock.FakeGroupRepository
import com.example.projectstudy.data.mock.FakeRankingRepository
import com.example.projectstudy.data.mock.FakeSessionRepository
import com.example.projectstudy.data.mock.FakeUserRepository
import com.example.projectstudy.data.repository.ActivityRepository
import com.example.projectstudy.data.repository.GroupRepository
import com.example.projectstudy.data.repository.RankingRepository
import com.example.projectstudy.data.repository.SessionRepository
import com.example.projectstudy.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindActivityRepository(
        repository: LocalActivityRepository
    ): ActivityRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(
        repository: LocalGroupRepository
    ): GroupRepository

    @Binds
    @Singleton
    abstract fun bindRankingRepository(
        repository: LocalRankingRepository
    ): RankingRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        repository: LocalUserRepository
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        repository: LocalSessionRepository
    ): SessionRepository
}