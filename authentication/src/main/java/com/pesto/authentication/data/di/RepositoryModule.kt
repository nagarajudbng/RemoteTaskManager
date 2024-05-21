package com.pesto.authentication.data.di

import android.content.SharedPreferences
import com.google.firebase.database.DatabaseReference
import com.pesto.authentication.data.repository.SignUpRepositoryImpl
import com.pesto.authentication.domain.repository.SignUpRepository
import com.pesto.core.data.source.local.dao.UserDao
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
     fun provideSignUpRepository(@Named("Auth") databaseReference: DatabaseReference,userDao: UserDao,sharedPreferences: SharedPreferences):SignUpRepository{
        return SignUpRepositoryImpl(databaseReference,userDao,sharedPreferences)
     }
}