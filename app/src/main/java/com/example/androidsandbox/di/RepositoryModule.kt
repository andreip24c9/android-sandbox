package com.example.androidsandbox.di

import com.example.androidsandbox.repository.SandboxRepository
import com.example.androidsandbox.repository.SandboxRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesSandboxRepository(): SandboxRepository {
        return SandboxRepositoryImpl()
    }
}