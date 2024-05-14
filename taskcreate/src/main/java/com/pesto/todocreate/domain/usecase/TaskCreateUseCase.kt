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


    suspend fun validate(task: Task): TaskResult {
        val taskResult = TaskResult()

        taskResult.title =
            if (task.title.isEmpty()) FieldStatus.FieldEmpty else FieldStatus.FieldFilled
        taskResult.description =
            if (task.description.isEmpty()) FieldStatus.FieldEmpty else FieldStatus.FieldFilled
        taskResult.status =
            if (task.status.isEmpty() || task.status == "Select") FieldStatus.FieldEmpty else FieldStatus.FieldFilled
        taskResult.isValid =
            !(taskResult.title == FieldStatus.FieldEmpty || taskResult.description == FieldStatus.FieldEmpty)

        return taskResult


    }

    suspend fun insert(task: Task): TaskResult {
        val result = localRepositoryImpl.insert(task.toTaskEntity())
        if(result>0){
            remoteRepositoryImpl.insert(result,task.toTaskEntity())
        }
        return TaskResult(result = result)
    }

}
