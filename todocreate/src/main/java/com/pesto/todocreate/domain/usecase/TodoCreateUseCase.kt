package com.pesto.todocreate.domain.usecase

import com.pesto.core.data.source.local.entity.Task
import com.pesto.core.domain.repository.TodoRepository

class TodoCreateUseCase(
    private var repository: TodoRepository
) {
    fun insert(task: Task) {
        repository.insert(task)
    }

    fun delete(task: Task) {
        repository.delete(task)
    }

    fun update(task: Task) {
        repository.update(task)
    }
}
