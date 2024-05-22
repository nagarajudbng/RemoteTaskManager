package com.single.todohome.usecases

import android.util.Log
import com.pesto.core.data.mapper.toUpdateTaskEntity
import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.repository.TaskRemoteRepositoryImpl
import com.pesto.core.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Created by Nagaraju Deshetty on 07/05/24.
class GetTaskListUseCase @Inject constructor(
    private var localRepositoryImpl: TaskLocalRepositoryImpl,
    private var remoteRepositoryImpl: TaskRemoteRepositoryImpl
) {
    suspend fun getTaskList(): Flow<List<Task>>{
        Log.d("Search HomeTaskUseCase","getTaskList")
        return localRepositoryImpl.getTaskList().map { taskEntityList ->
            taskEntityList.map { taskEntity ->
                Task(
                    id = taskEntity.id,
                    title = taskEntity.title,
                    description = taskEntity.description,
                    status = taskEntity.status,
                    dueDate = taskEntity.dueDate
                )
            }
        }
    }

}