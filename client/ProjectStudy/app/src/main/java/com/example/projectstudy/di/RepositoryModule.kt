package com.example.projectstudy.di

import com.example.projectstudy.data.mock.FakeActivityRepository
import com.example.projectstudy.domain.repository.ActivityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideActivityRepository(): ActivityRepository {
        return FakeActivityRepository()
    }
}