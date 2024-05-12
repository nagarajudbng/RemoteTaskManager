package com.pesto.core.data.repository

import com.pesto.core.data.source.local.AppDatabase
import com.pesto.core.data.source.local.entity.Task
import com.pesto.core.domain.repository.RowId
import com.pesto.core.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private var appDatabase: AppDatabase
):TaskRepository {
    override fun insert(task: Task): RowId {
        return appDatabase.taskDao.insert(task)
    }

    override fun delete(task: Task) {
        appDatabase.taskDao.delete(task)
    }

    override fun update(task: Task) {
        appDatabase.taskDao.update(task)
    }

}
