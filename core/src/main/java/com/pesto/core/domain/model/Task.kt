package com.pesto.core.domain.model

data class Task(
    var id: Long = 0,
    var title: String,
    var description: String,
    var status: String,
    var dueDate: String,
    var alarmTime: String,
    var isDueDateOver:Boolean = false
)
