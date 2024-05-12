package com.pesto.core.data.repository

import com.pesto.core.data.source.local.AppDatabase
import com.pesto.core.data.source.local.entity.TaskEntity
import com.pesto.core.domain.repository.RowId
import com.pesto.core.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private var appDatabase: AppDatabase
):TaskRepository {
    override suspend fun insert(task: TaskEntity): RowId {
        return appDatabase.taskDao.insert(task)
    }

    override fun delete(task: TaskEntity) {
        appDatabase.taskDao.delete(task)
    }

    override fun update(task: TaskEntity) {
        appDatabase.taskDao.update(task)
    }


    override suspend fun getTaskList(): Flow<List<TaskEntity>> {
        val taskDao = appDatabase.taskDao
        return taskDao.getTaskList()
    }

    override suspend fun searchQuery(query: String): Flow<List<TaskEntity>> {
        val taskDao = appDatabase.taskDao
        return  taskDao.search(query)
    }

}
