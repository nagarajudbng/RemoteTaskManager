package com.pesto.core.domain.repository

import com.pesto.core.data.source.local.entity.TaskEntity


// Created by Nagaraju on 14/05/24.

interface RemoteRepository {
    suspend fun insert(id:Long,task: TaskEntity): RowId
    suspend fun delete(task: TaskEntity)

    suspend fun update(task: TaskEntity)
}