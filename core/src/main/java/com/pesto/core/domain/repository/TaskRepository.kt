package com.pesto.core.domain.repository

import com.pesto.core.data.source.local.entity.Task


// Created by Nagaraju on 13/05/24.
typealias  RowId = Long
interface TaskRepository {
    fun insert(task: Task): RowId

    fun delete(task: Task)

    fun update(task: Task)
}