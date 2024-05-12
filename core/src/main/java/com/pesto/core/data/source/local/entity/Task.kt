package com.pesto.core.data.source.local.entity

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    var title: String,
    var description: String,
    var status: String
)
