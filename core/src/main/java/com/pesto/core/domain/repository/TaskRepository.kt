package com.pesto.core.domain.repository

import com.pesto.core.data.source.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow


// Created by Nagaraju on 13/05/24.
typealias  RowId = Long
interface TaskRepository {
    suspend fun insert(task: TaskEntity): RowId

    suspend fun delete(task: TaskEntity)

    suspend fun update(task: TaskEntity)
    suspend fun getTaskList(): Flow<List<TaskEntity>>
    suspend fun searchQuery(query: String): Flow<List<TaskEntity>>
    suspend fun filter(query: String): Flow<List<TaskEntity>>
}