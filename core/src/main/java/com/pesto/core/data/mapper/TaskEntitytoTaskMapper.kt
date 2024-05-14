package com.pesto.core.data.mapper

import com.pesto.core.data.dto.TaskDB
import com.pesto.core.data.source.local.entity.TaskEntity
import com.pesto.core.domain.model.Task


// Created by Nagaraju on 13/05/24.

fun TaskEntity.toTask():Task{

    return Task(
        title  = title,
        description = description,
        status = status,
        dueDate = dueDate
    )
}

fun TaskEntity.toTaskDB(id:String):TaskDB{
    return TaskDB(
        id = id,
    title  = title,
    description = description,
    status = status,
    dueDate =dueDate

    )
}

fun Task.toTaskEntity():TaskEntity{
    return TaskEntity(
        id = 0,
        title = title,
        description=description,
        status = status,
        dueDate = dueDate
    )
}

fun Task.toUpdateTaskEntity():TaskEntity{
    return TaskEntity(
        id = id,
        title = title,
        description=description,
        status = status,
        dueDate =dueDate
    )
}