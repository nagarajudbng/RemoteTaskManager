package com.pesto.core.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pesto.core.data.source.local.AppDatabase
import com.pesto.core.data.source.local.dao.ProfileDao
import com.pesto.core.data.source.local.dao.TaskDao
import com.pesto.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    fun provideTaskDao(appDatabase: AppDatabase):TaskDao{
        return appDatabase.taskDao
    }
    @Provides
    @Singleton
    fun provideProfileDao(appDatabase: AppDatabase):ProfileDao{
        return appDatabase.profileDao
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences {
        return app.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }


    @Provides
    @Singleton
    fun provideFireBaseDatabase(): FirebaseDatabase {
        var firebaseDatabase = Firebase.database
        firebaseDatabase.setPersistenceEnabled(true)
        return firebaseDatabase
    }

    @Provides
    @Singleton
    @Named("Task")
    fun provideTaskDatabaseReference(firebaseDatabase: FirebaseDatabase):DatabaseReference{
        val databaseReference = firebaseDatabase.reference.child("task")
        databaseReference.keepSynced(true)
        return databaseReference
    }
    @Provides
    @Singleton
    @Named("Auth")
    fun provideAuthenticationReference(firebaseDatabase: FirebaseDatabase):DatabaseReference{
        val databaseReference = firebaseDatabase.reference.child("auth")
        databaseReference.keepSynced(true)
        return databaseReference
    }

}