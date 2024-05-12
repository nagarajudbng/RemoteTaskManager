package com.pesto.core.domain.states

import com.pesto.core.domain.repository.RowId


// Created by Nagaraju Deshetty on 07/05/


data class TaskResult(
    var isValid:Boolean = false,
    var title: FieldStatus?= null,
    var description:FieldStatus? = null,
    val result: RowId?=null
)