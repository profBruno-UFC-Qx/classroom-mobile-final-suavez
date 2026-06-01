package com.example.projectstudy.di

import com.example.projectstudy.data.mock.FakeActivityRepository
import com.example.projectstudy.data.mock.FakeGroupRepository
import com.example.projectstudy.data.mock.FakeRankingRepository
import com.example.projectstudy.data.mock.FakeUserRepository
import com.example.projectstudy.domain.repository.ActivityRepository
import com.example.projectstudy.domain.repository.GroupRepository
import com.example.projectstudy.domain.repository.RankingRepository
import com.example.projectstudy.domain.repository.UserRepository
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
        repository: FakeActivityRepository
    ): ActivityRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(
        repository: FakeGroupRepository
    ): GroupRepository

    @Binds
    @Singleton
    abstract fun bindRankingRepository(
        repository: FakeRankingRepository
    ): RankingRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        repository: FakeUserRepository
    ): UserRepository
}