package com.pesto.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pesto.core.data.source.local.dao.TaskDao
import com.pesto.core.data.source.local.dao.UserDao
import com.pesto.core.data.source.local.entity.TaskEntity
import com.pesto.core.data.source.local.entity.UserEntity


@Database(entities = [TaskEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract val taskDao:TaskDao
    abstract val userDao:UserDao
}
