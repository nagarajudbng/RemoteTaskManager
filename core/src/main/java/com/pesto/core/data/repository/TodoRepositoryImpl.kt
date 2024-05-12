package com.pesto.core.data.repository

import com.pesto.core.data.source.local.AppDatabase
import com.pesto.core.data.source.local.entity.Task

class TodoRepositoryImpl(
    private var appDatabase: AppDatabase
) {
    fun insert(task: Task) {
        appDatabase.taskDao.insert(task)
    }

    fun delete(task: Task) {
        appDatabase.taskDao.delete(task)
    }

}
