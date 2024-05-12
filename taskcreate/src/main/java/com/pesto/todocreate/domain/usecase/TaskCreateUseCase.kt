package com.pesto.todocreate.domain.usecase

import com.pesto.core.data.source.local.entity.Task
import com.pesto.core.domain.repository.TaskRepository
import com.pesto.core.domain.states.FieldStatus
import com.pesto.core.domain.states.TaskResult

class TaskCreateUseCase(
    private var repository: TaskRepository
) {


    suspend fun validate(task: Task): TaskResult {
        var taskResult= TaskResult()

        if (task.title?.contains("Error") == true) {
            throw IllegalArgumentException("Title cannot contain 'Error' text.")
        }
        taskResult.title = if(task.title?.isEmpty() == true || task.title.equals("Error"))
            FieldStatus.FieldEmpty else FieldStatus.FieldFilled
        taskResult.description = if(task.description?.isEmpty() == true || task.description.equals("Error"))
            FieldStatus.FieldEmpty else FieldStatus.FieldFilled
        taskResult.isValid = !(taskResult.title == FieldStatus.FieldEmpty
                ||taskResult.description == FieldStatus.FieldEmpty)

        return taskResult


    }
    suspend fun insert(task: Task): TaskResult {
        val result = repository.insert(task)
        return TaskResult(result = result)
    }

    fun delete(task: Task) {
        repository.delete(task)
    }

    fun update(task: Task) {
        repository.update(task)
    }
}
