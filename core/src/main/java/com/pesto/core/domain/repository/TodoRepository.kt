package com.pesto.core.domain.repository

import com.pesto.core.data.source.local.entity.Task


// Created by Nagaraju on 13/05/24.

interface TodoRepository {
    fun insert(task: Task)

    fun delete(task: Task)

    fun update(task: Task)
}