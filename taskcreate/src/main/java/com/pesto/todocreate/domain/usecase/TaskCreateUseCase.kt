package com.pesto.todocreate.domain.usecase

import com.pesto.core.data.mapper.toTaskEntity
import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.repository.TaskRemoteRepositoryImpl
import com.pesto.core.domain.model.Task
import com.pesto.core.domain.repository.TaskRepository
import com.pesto.core.domain.states.FieldStatus
import com.pesto.core.domain.states.TaskResult
import javax.inject.Inject

class TaskCreateUseCase @Inject constructor(
    private var localRepositoryImpl: TaskLocalRepositoryImpl,
    private var remoteRepositoryImpl: TaskRemoteRepositoryImpl
) {

    suspend fun insert(task: Task): TaskResult {
        val result = localRepositoryImpl.insert(task.toTaskEntity())
        if(result>0){
            remoteRepositoryImpl.insert(result,task.toTaskEntity())
        }
        return TaskResult(result = result)
    }

}
