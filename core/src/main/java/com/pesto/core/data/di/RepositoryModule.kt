package com.pesto.core.data.di

import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.repository.TaskRemoteRepositoryImpl
import com.pesto.core.data.source.local.AppDatabase
import com.pesto.core.domain.repository.RemoteRepository
import com.pesto.core.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// Created by Nagaraju on 14/05/24.

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    @Singleton
    abstract fun provideLocalRepository(taskLocalRepositoryImpl: TaskLocalRepositoryImpl): TaskRepository

    @Binds
    @Singleton
    abstract fun provideRemoteRepository(taskRemoteRepositoryImpl: TaskRemoteRepositoryImpl):RemoteRepository
}