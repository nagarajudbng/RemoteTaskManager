package com.single.todohome.usecases

import com.pesto.core.data.mapper.toUpdateTaskEntity
import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.repository.TaskRemoteRepositoryImpl
import com.pesto.core.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Created by Nagaraju Deshetty on 07/05/24.
class HomeTaskUseCase @Inject constructor(
    private var localRepositoryImpl: TaskLocalRepositoryImpl,
    private var remoteRepositoryImpl: TaskRemoteRepositoryImpl
) {
    suspend fun getTaskList(): Flow<List<Task>>{
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

     suspend fun searchQuery(query: String): Flow<List<Task>> {
         return localRepositoryImpl.searchQuery(query).map { taskEntityList ->
             taskEntityList.map { taskEntity ->
                 Task(
                     id=taskEntity.id,
                     title = taskEntity.title,
                     description = taskEntity.description,
                     status = taskEntity.status,
                     dueDate = taskEntity.dueDate
                 )
             }
         }
    }
    suspend fun filter(query: String): Flow<List<Task>> {
        return localRepositoryImpl.filter(query).map { taskEntityList ->
            taskEntityList.map { taskEntity ->
                Task(
                    id=taskEntity.id,
                    title = taskEntity.title,
                    description = taskEntity.description,
                    status = taskEntity.status,
                    dueDate = taskEntity.dueDate
                )
            }
        }
    }
    suspend fun delete(task: Task) {
        localRepositoryImpl.delete(task.toUpdateTaskEntity())
        remoteRepositoryImpl.delete(task.toUpdateTaskEntity())
    }

    suspend fun update(task: Task) {
        localRepositoryImpl.update(task.toUpdateTaskEntity())
        remoteRepositoryImpl.update(task.toUpdateTaskEntity())
    }
}