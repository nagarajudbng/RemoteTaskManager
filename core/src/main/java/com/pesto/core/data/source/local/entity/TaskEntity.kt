package com.pesto.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TaskEntity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    var title: String,
    var description: String,
    var status: String,
    var dueDate:String,
    var alarmTime:String
)
