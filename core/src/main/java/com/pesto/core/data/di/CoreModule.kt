package com.pesto.core.data.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pesto.core.data.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// Created by Nagaraju on 13/05/24.

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "task"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFireBaseDatabase():DatabaseReference{
        val firebaseDatabase = Firebase.database
        firebaseDatabase.setPersistenceEnabled(true)
        val databaseReference = firebaseDatabase.reference.child("task")
        databaseReference.keepSynced(true)
        return databaseReference
    }

}