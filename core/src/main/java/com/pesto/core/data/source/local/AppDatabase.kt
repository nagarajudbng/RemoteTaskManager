package com.pesto.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pesto.core.data.source.local.dao.TaskDao
import com.pesto.core.data.source.local.entity.TaskEntity


@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract val taskDao:TaskDao
}
