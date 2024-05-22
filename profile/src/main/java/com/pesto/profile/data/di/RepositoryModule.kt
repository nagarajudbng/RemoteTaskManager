package com.pesto.profile.data.di

import android.content.SharedPreferences
import com.google.firebase.database.DatabaseReference
import com.pesto.core.data.source.local.dao.ProfileDao
import com.pesto.profile.data.repository.ProfileRepositoryImpl
import com.pesto.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


// Created by Nagaraju on 14/05/24.

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{
    @Provides
    @Singleton
     fun provideProfileRepository(@Named("Auth") databaseReference: DatabaseReference,profileDao:ProfileDao,sharedPreferences: SharedPreferences):ProfileRepository{
        return ProfileRepositoryImpl(databaseReference,profileDao,sharedPreferences)
     }
}